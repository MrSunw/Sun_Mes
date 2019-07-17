package com.sun.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.sun.beans.PageQuery;
import com.sun.beans.PageResult;
import com.sun.dao.MesProductCustomerMapper;
import com.sun.dao.MesProductMapper;
import com.sun.dto.ProductDto;
import com.sun.dto.SearchProductDto;
import com.sun.exception.SysMineException;
import com.sun.model.MesProduct;
import com.sun.param.BindProductParam;
import com.sun.param.MesProductVo;
import com.sun.param.SearchProductParam;
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
	
	//查询批量到库分页 和到库分页
	public PageResult<ProductDto> productSelect(SearchProductParam param, PageQuery page) {
		// 验证页码是否为空
		BeanValidator.check(page);
		//将param字段传入dto
		SearchProductDto  dto=new SearchProductDto();
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		if(StringUtils.isNotBlank(param.getSearch_source())) {
			dto.setSearch_source(param.getSearch_source());
		}
		if(StringUtils.isNotBlank(param.getSearch_status())) {
			dto.setSearch_status(Integer.parseInt(param.getSearch_status()));
		}
		//查询所给条件返回数量
		int count=mesProductCustomerMapper.countBySearchDto(dto);
		if(count>0) {
			List<ProductDto> productList=(List<ProductDto>) mesProductCustomerMapper.getPageListSearchDto(dto,page);
			return PageResult.<ProductDto>builder().total(count).data(productList).build();
		}
		return PageResult.<ProductDto>builder().build();
	}
	
	//修改
	public void update(MesProductVo mesProductVo) {
		 BeanValidator.check(mesProductVo);
		 MesProduct product=mesProductMapper.selectByPrimaryKey(mesProductVo.getId());
		 Preconditions.checkNotNull(product,"待更新材料不存在");
		 try {
			
			//将vo转换为po
			 product.setProductImgid(mesProductVo.getProductImgid());
				product.setProductIrontype(mesProductVo.getProductIrontype());
				product.setProductIrontypeweight(mesProductVo.getProductIrontypeweight());
				product.setProductMaterialname(mesProductVo.getProductMaterialname());
				product.setProductTargetweight(mesProductVo.getProductTargetweight());
				product.setProductMaterialsource(mesProductVo.getProductMaterialsource());
				product.setProductRemark(mesProductVo.getProductRemark());
				product.setProductRealweight(mesProductVo.getProductRealweight());
				
				float temp=product.getProductLeftweight()-product.getProductBakweight();
				float leftweight=product.getProductLeftweight();
				
				//剩余重量备份需要重新设置
				product.setProductLeftweight(mesProductVo.getProductLeftweight());
				product.setProductBakweight(product.getProductLeftweight()-temp);
				
				product.setProductOperateTime(new Date());
				if(leftweight>=temp)
				mesProductMapper.updateByPrimaryKeySelective(product);
			 
		} catch (Exception e) {
			throw new SysMineException("修改过程有问题");
		}
	}
	
	
	
	//批量到库
	public void productBatchStart(String ids) {
		//144&145
		if(ids!=null&&ids.length()>0) {
			MesProductMapper mapper=sqlSession.getMapper(MesProductMapper.class);
			String[] idArray=ids.split("&");
			//把product_status 改为1
			for(String id:idArray) {
				MesProduct mesProduct=mapper.selectByPrimaryKey(Integer.parseInt(id));
				mesProduct.setProductStatus(1);
				mesProduct.setProductOperateTime(new Date());
				mapper.updateByPrimaryKey(mesProduct);
			}
			
		}
		
	}
	
	//材料绑定分页
	public PageResult<ProductDto>  productBindList(SearchProductParam param, PageQuery page) {
		//检验页码
		BeanValidator.check(page);
		
		SearchProductDto dto=new SearchProductDto();
		//param转换为dto
		if(StringUtils.isNotBlank(param.getSearch_source())) {
			dto.setSearch_source(param.getSearch_source());
		}
		if(param.getSearch_status()!=null) {
			dto.setSearch_status(Integer.parseInt(param.getSearch_status()));
		}
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		int counts=mesProductCustomerMapper.countBindBySearchDto(dto);
		if(counts>0) {
			List<ProductDto> list=mesProductCustomerMapper.getPageBindListSearchDto(dto,page);
			 return PageResult.<ProductDto>builder().total(counts).data(list).build();
		}
		return PageResult.<ProductDto>builder().build();
	}
	
	//获取需要绑定的材料
	public MesProduct seletById(String id) {
		MesProduct pd=mesProductMapper.selectByPrimaryKey(Integer.parseInt(id));
		return pd;
	}
	
	//查询绑定钢材分页
	public PageResult<ProductDto> productBindleft(SearchProductParam param, PageQuery page) {
		BeanValidator.check(page);
		SearchProductDto dto=new SearchProductDto();
		//param转换为dto
		if(StringUtils.isNotBlank(param.getSearch_source())) {
			dto.setSearch_source(param.getSearch_source());
		}
		if(param.getSearch_status()!=null) {
			dto.setSearch_status(Integer.parseInt(param.getSearch_status()));
		}
		List<ProductDto> list=mesProductCustomerMapper.getBindListSearchDto(param,page);
		return PageResult.<ProductDto>builder().data(list).build();
	}
	
	
	//绑定材料事件
	public void productBindEvent(BindProductParam param) {
		BeanValidator.check(param);
		MesProduct parent=mesProductMapper.selectByPrimaryKey(param.getParentId());
		MesProduct child=mesProductMapper.selectByPrimaryKey(param.getProductchildId());
		float temp=param.getProductLeftbackweight()-param.getProductChildTargetweight();
		parent.setProductBakweight(temp);
		child.setProductStatus(1);
		child.setProductBakweight(child.getProductTargetweight());
		child.setpId(param.getParentId());
		mesProductMapper.updateByPrimaryKeySelective(parent);
		mesProductMapper.updateByPrimaryKeySelective(child);
		
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
