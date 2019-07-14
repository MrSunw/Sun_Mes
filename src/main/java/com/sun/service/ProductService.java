package com.sun.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.sun.dao.MesProductCustomerMapper;
import com.sun.dao.MesProductMapper;
import com.sun.exception.SysMineException;
import com.sun.model.MesProduct;
import com.sun.param.MesProductVo;
import com.sun.util.BeanValidator;

@Service
public class ProductService {

	@Resource
	private MesProductMapper mesProductMapper;
	
	@Resource
	private MesProductCustomerMapper mesProductCustomerMapper;
	
	@Resource
	private SqlSession sqlSession;
	
	//一开始就定义一个id生成器
	private IdGenerator ig=new IdGenerator();
	
	
	
	//批量添加材料
	public void productBatchInserts(MesProductVo mesProductVo) {
		//数据校验
		BeanValidator.check(mesProductVo);
		//判断是否批量增加
		Integer counts=mesProductVo.getCounts();
		
		List<String> ids=createProductIdsDefault(Long.valueOf(counts));
		//sql的批量处理
		MesProductMapper mesProductBatchMapper=sqlSession.getMapper(MesProductMapper.class);
		for(String productid:ids) {
			try {
				//将vo转换为po
				MesProduct mesProduct=MesProduct.builder().productId(productid).productOrderid(mesProductVo.getProductOrderid())
						.productPlanid(mesProductVo.getProductPlanid()).productTargetweight(mesProductVo.getProductTargetweight())
						.productRealweight(mesProductVo.getProductRealweight()).productLeftweight(mesProductVo.getProductLeftweight())
						.productBakweight(mesProductVo.getProductLeftweight()).productIrontypeweight(mesProductVo.getProductIrontypeweight())
						.productIrontype(mesProductVo.getProductIrontype()).productMaterialname(mesProductVo.getProductMaterialname())
						.productImgid(mesProductVo.getProductImgid()).productMaterialsource(mesProductVo.getProductMaterialsource())
						.productStatus(mesProductVo.getProductStatus()).productRemark(mesProductVo.getProductRemark()).build();
				
				//设置用户的登录信息
				mesProduct.setProductOperator("user01");
				mesProduct.setProductOperateIp("127.0.0.1");
				mesProduct.setProductOperateTime(new Date());
				
				mesProductBatchMapper.insertSelective(mesProduct);
				
			} catch (Exception e) {
				throw new SysMineException("创建过程有问题");
			}
		}
	}
	/////////////////////////////////////////////
	//获取数据库所有数量  往后增加材料
	public Long getProductCount() {
		return mesProductCustomerMapper.getProductCount();
		
	}
	//获取id集合
	public List<String > createProductIdsDefault(Long ocounts){
		if(ig==null) {
			ig=new IdGenerator();
		}
		ig.setCurrentabidscount(getProductCount());
		List<String> list=ig.initIds(ocounts);
		ig.clear();
		return list;
	}
	///////////////////////////////////////////////
	//id生成器
	class IdGenerator{
		//数量起始位置
		private Long currentabidscount;
		private List<String> ids=new ArrayList<String>();
		private String idpre;//前缀  ZX_P_
		private String yearstr;
		private String idafter;//后缀
		
		public IdGenerator() {
		
		}
		public Long getCurrentabidscount() {
			return currentabidscount;
		}
		public void setCurrentabidscount(Long currentabidscount) {
			this.currentabidscount = currentabidscount;
			if(null==this.ids) {
				this.ids=new ArrayList<String>();
			}
		}
		public List<String> getIds() {
			return ids;
		}
		public void setIds(List<String> ids) {
			this.ids = ids;
		}
		public String getIdpre() {
			return idpre;
		}
		public void setIdpre(String idpre) {
			this.idpre = idpre;
		}
		public String getYearstr() {
			return yearstr;
		}
		public void setYearstr(String yearstr) {
			this.yearstr = yearstr;
		}
		public String getIdafter() {
			return idafter;
		}
		public void setIdafter(String idafter) {
			this.idafter = idafter;
		}
		
	    //获取前缀
		private String getIdPre() {
			this.idpre="ZX_P_";
			return this.idpre;
		}
		//获取后缀
		private String getIdAfter(int addcount) {
			//系统默认后缀生成6位 000001
			int goallength=6;
			//+1+循环次数(addcount)+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//currentabidscount 从数据库获取的数量
			int count=this.currentabidscount.intValue()+1+addcount;
			StringBuilder sBuilder=new StringBuilder("");
			//计算与6位数的差值
			int length=goallength-new String(count+"").length();// count是1位  5-1  就有5位是0 count有个位数  就有4个0 
			for(int i=0;i<length;i++) {
				sBuilder.append("0");
			}//得到多少个0
			sBuilder.append(count+"");//得到00001
			return sBuilder.toString();
		}
		
		//拼装产品编号
		public List<String> initIds(Long ocounts){
			for(int i=0;i<ocounts;i++) {
				this.ids.add(getIdPre()+getIdAfter(i));
			}
			return this.ids;
		}
		public void clear() {
			this.ids=null;
		}
	}
}
