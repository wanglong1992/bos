package cn.itcast.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.domain.base.Area;
import cn.itcast.service.AreaService;
import cn.itcast.utils.PinYin4jUtils;
import cn.itcast.web.action.common.BaseAction;

@Controller
@Scope(value = "prototype")
@ParentPackage("json-default")
@Namespace("/base")
public class AreaAction extends BaseAction<Area> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private AreaService areaService;

	@Action(value = "area_pageQuery", results = { @Result(name = SUCCESS, type = "json") })
	public String areaPageQuery() {
		// 无条件分页查询
		// Pageable pageable = new PageRequest(page - 1, rows, new
		// Sort("courierNum"));
		// Page<Courier> page = courierService.pageQuery(pageable);
		// pushValueStackToPage(page);

		// 条件分页查询
		PageRequest pageable = new PageRequest(page - 1, rows);
		Specification<Area> specification = new Specification<Area>() {
			/**
			 * 传递： Root<Courier>
			 * root：（连接语句的时候需要字段，获取字段的名称）代表Criteria查询的根对象，Criteria查询的查询根定义了实体类型，能为将来导航获得想要的结果，它与SQL查询中的FROM子句类似
			 * CriteriaQuery<?> query：
			 * （简单的查询可以使用CriteriaQuery）代表一个specific的顶层查询对象，它包含着查询的各个部分，比如：select
			 * 、from、where、group by、order by等 CriteriaBuilder
			 * cb：（复杂的查询可以使用CriteriaBuilder构建）用来构建CritiaQuery的构建器对象
			 * 返回：Predicate：封装查询条件
			 * 
			 */
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				List<Predicate> list = new ArrayList<Predicate>();

				if (StringUtils.isNoneBlank(model.getProvince())) {
					Predicate p = cb.like(root.get("province").as(String.class), "%" + model.getProvince() + "%");
					list.add(p);
				}
				if (StringUtils.isNoneBlank(model.getCity())) {
					Predicate p = cb.like(root.get("city").as(String.class), "%" + model.getCity() + "%");
					list.add(p);
				}

				if (StringUtils.isNotBlank(model.getDistrict())) {
					Predicate p = cb.like(root.get("district").as(String.class), "%" + model.getDistrict() + "%");
					list.add(p);
				}

				if (list != null && list.size() > 0) {
					Predicate[] predicates = new Predicate[list.size()];
					Predicate[] array = list.toArray(predicates);
					query.where(array);
				}

				query.orderBy(cb.asc(root.get("id").as(String.class)));
				predicate = query.getRestriction();
				return predicate;
				// 若有这个条件,后面的都不需要拼接了
				// if (StringUtils.isNoneBlank(model.getCourierNum())) {
				// Predicate p =
				// cb.equal(root.get("courierNum").as(String.class),
				// model.getCourierNum());
				// list.add(p);
				// } else {
				// if (StringUtils.isNotBlank(model.getCompany())) {
				// Predicate p =
				// cb.like(root.get("company").as(String.class),
				// "%" + model.getCompany() + "%");
				// list.add(p);
				// }
				// if (StringUtils.isNotBlank(model.getType())) {
				// Predicate p = cb.like(root.get("type").as(String.class),
				// "%"
				// + model.getType() + "%");
				// list.add(p);
				// }
				//
				// Join<Courier, Standard> join = root.join("standard",
				// JoinType.INNER);
				// if (model.getStandard() != null &&
				// model.getStandard().getId() != null) {
				// Predicate p = cb.equal(join.get("id").as(Integer.class),
				// model.getStandard().getId());
				// list.add(p);
				// }
				// }

				// 第一种方法
				// if (list != null && list.size() > 0) {
				// Predicate [] p = new Predicate[list.size()];
				// predicate = cb.and(list.toArray(p));
				// }
				// 第二种方法
				// if (list != null && list.size() > 0) {
				// Predicate[] p = new Predicate[list.size()];
				// query.where(list.toArray(p));
				// }
				//
				// // 排序
				// query.orderBy(cb.asc(root.get("courierNum").as(String.class)));
				//
				// predicate = query.getRestriction();
				// return predicate;
			}

		};

		Page<Area> page = areaService.findPageQuery(specification, pageable);
		pushValueStackToPage(page);

		return SUCCESS;
	}

	private File file;
	private String fileFileName;

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Action(value = "area_importData", results = { @Result(type = "json") })
	public String areaImportData() throws IOException {
		System.out.println(fileFileName);
		String fileType = fileFileName.substring(fileFileName.lastIndexOf(".") + 1);
		System.out.println(fileType);
		List<Area> areas = new ArrayList<Area>();

		if ("xls".equals(fileType)) {

			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
			// 实际中要考虑多页情况,或者注明只有第一张sheet有效
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			for (Row row : sheet) {
				if (row.getRowNum() == 0) {
					continue;
				}
				if (row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
					continue;
				}
				Area area = new Area();
				area.setId(row.getCell(0).getStringCellValue());// 区域编号
				area.setProvince(row.getCell(1).getStringCellValue());// 省份
				area.setCity(row.getCell(2).getStringCellValue());// 城市
				area.setDistrict(row.getCell(3).getStringCellValue());// 区域
				area.setPostcode(row.getCell(4).getStringCellValue());// 邮编
				// 简码
				String province = area.getProvince().substring(0, area.getProvince().length() - 1);
				String city = area.getCity().substring(0, area.getCity().length() - 1);
				String district = area.getDistrict().substring(0, area.getDistrict().length() - 1);
				String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
				StringBuilder sb = new StringBuilder();
				for (String head : headByString) {
					sb.append(head);
				}
				area.setShortcode(sb.toString());
				String citycode = PinYin4jUtils.hanziToPinyin(city, "");
				area.setCitycode(citycode);
				areas.add(area);

			}
			hssfWorkbook.close();
		} else if ("xlsx".equals(fileType)) {
			XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(new FileInputStream(file));
			Sheet sheet = xSSFWorkbook.getSheetAt(0);

			for (Row row : sheet) {
				if (row.getRowNum() == 0) {
					continue;
				}
				if (row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
					continue;
				}
				Area area = new Area();
				area.setId(row.getCell(0).getStringCellValue());// 区域编号
				area.setProvince(row.getCell(1).getStringCellValue());// 省份
				area.setCity(row.getCell(2).getStringCellValue());// 城市
				area.setDistrict(row.getCell(3).getStringCellValue());// 区域
				area.setPostcode(row.getCell(4).getStringCellValue());// 邮编
				String province = area.getProvince().substring(0, area.getProvince().length() - 1);
				String city = area.getCity().substring(0, area.getCity().length() - 1);
				String district = area.getDistrict().substring(0, area.getDistrict().length() - 1);
				String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
				StringBuilder sb = new StringBuilder();
				for (String head : headByString) {
					sb.append(head);
				}
				area.setShortcode(sb.toString());
				String citycode = PinYin4jUtils.hanziToPinyin(city, "");
				area.setCitycode(citycode);
				areas.add(area);
			}
			xSSFWorkbook.close();
		} else {
			throw new RuntimeException("文件不是excel格式");
		}

		areaService.saveList(areas);
		return SUCCESS;
	}
}
