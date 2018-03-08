package com.zhou.test.bds.milk.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.impl.NutDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Date;

/**
 * @author eli
 * @date 2018/2/1 12:21
 */
@Table("t_company")
public class TCompany {
    @Id
    private Long id;
    @Column
    private String company_name;
    @Column
    private String shareholers;
    @Column
    private String core_business;
    @Column
    private String market_position_china;
    @Column
    private String market_position_ww;
    @Column
    private String establishment;
    @Column
    private String detail_link;
    @Column
    private String home_link;
    @Column
    private String plant_num;
    @Column
    private String employee_num;
    @Column
    private String rd_num;
    @Column
    private String office_distributor_num;
    @Column
    private Date create_time;
    @Column
    private Date update_time;
    @Column
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getShareholers() {
        return shareholers;
    }

    public void setShareholers(String shareholers) {
        this.shareholers = shareholers;
    }

    public String getCore_business() {
        return core_business;
    }

    public void setCore_business(String core_business) {
        this.core_business = core_business;
    }

    public String getMarket_position_china() {
        return market_position_china;
    }

    public void setMarket_position_china(String market_position_china) {
        this.market_position_china = market_position_china;
    }

    public String getMarket_position_ww() {
        return market_position_ww;
    }

    public void setMarket_position_ww(String market_position_ww) {
        this.market_position_ww = market_position_ww;
    }

    public String getEstablishment() {
        return establishment;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public void setDetail_link(String detail_link) {
        this.detail_link = detail_link;
    }

    public String getHome_link() {
        return home_link;
    }

    public void setHome_link(String home_link) {
        this.home_link = home_link;
    }

    public String getPlant_num() {
        return plant_num;
    }

    public void setPlant_num(String plant_num) {
        this.plant_num = plant_num;
    }

    public String getEmployee_num() {
        return employee_num;
    }

    public void setEmployee_num(String employee_num) {
        this.employee_num = employee_num;
    }

    public String getRd_num() {
        return rd_num;
    }

    public void setRd_num(String rd_num) {
        this.rd_num = rd_num;
    }

    public String getOffice_distributor_num() {
        return office_distributor_num;
    }

    public void setOffice_distributor_num(String office_distributor_num) {
        this.office_distributor_num = office_distributor_num;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/com/zhou/test/bds/milk/dataSource.xml");
        NutDao biDao = (NutDao) ctx.getBean("biDao");
        biDao.create(TCompany.class,false);

    }
}
