package com.example.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@BatchSize(size = 100)
@Entity
@Table(name = "TBL_RESTAURANT_INFO_L")
public class RestaurantInfo extends CreaDtUpdtDtBase {

    @Id
    @Column(name="NO")
    private Long no;

    @Column(name="OPN_SVC_NM")
    private String opnSvcNm;
    @Column(name="OPN_SVC_ID")
    private String opnSvcId;
    @Column(name="OPN_SF_TEAM_CODE")
    private String opnSfTeamCode;
    @Column(name="MGT_NO")
    private String mgtNo;
    @Column(name="APV_PERM_YMD")
    private String apvPermYmd;
    @Column(name="APV_CANCEL_YMD")
    private String apvCancelYmd;
    @Column(name="TRD_STATE_GBN")
    private String trdStateGbn;
    @Column(name="TRD_STATE_NM")
    private String trdStateNm;
    @Column(name="DTL_STATE_GBN")
    private String dtlStateGbn;
    @Column(name="DTL_STATE_NM")
    private String dtlStateNm;
    @Column(name="DCB_YMD")
    private String dcbYmd;
    @Column(name="CLG_ST_DT")
    private String clgStdt;
    @Column(name="CLG_END_DT")
    private String clgEnddt;
    @Column(name="ROPN_YMD")
    private String ropnYmd;

    @Column(name="SITE_TEL")
    private String siteTel;
    @Column(name="SITE_AREA")
    private String siteArea;
    @Column(name="SITE_POST_NO")
    private String sitePostNo;
    @Column(name="SITE_WHL_ADDR")
    private String siteWhlAddr;
    @Column(name="RDN_WHL_ADDR")
    private String rdnWhlAddr;
    @Column(name="RDN_POST_NO")
    private String rdnPostNo;
    @Column(name="BPLC_NM")
    private String bplcNm;
    @Column(name="LAST_MOD_TS")
    private String lastModTs;
    @Column(name="DATA_UPDATE_GBN")
    private String dataUpdateGbn;
    @Column(name="DATA_UPDATE_DT")
    private String dataUpdateDt;
    @Column(name="UPTAE_NM")
    private String uptaeNm;
    @Column(name="POS_X")
    private Double posX;
    @Column(name="POS_Y")
    private Double posY;

    @Column(name="HGY_UPTAE_NM")
    private String hgyUptaeNm;
    @Column(name="MALE_EMP_CNT")
    private Integer maleEmpCnt;
    @Column(name="FEMALE_EMP_CNT")
    private Integer femaleEmpCnt;
    @Column(name="BIZ_AREA_NM")
    private String bizAreaNm;
    @Column(name="GRD_GBN_NM")
    private String grdGbnNm;
    @Column(name="WTR_FCLTY_GBN_NM")
    private String wtrFlctyGbnNm;
    @Column(name="TOT_EMP_CNT")
    private Integer totEmpCnt;
    @Column(name="HQ_EMP_CNT")
    private Integer hqEmpCnt;
    @Column(name="FACT_OFC_EMP_CNT")
    private Integer fctyOfcEmpCnt;
    @Column(name="FACT_SLS_EMP_CNT")
    private Integer factSlsEmpCnt;
    @Column(name="FACT_PRD_EMP_CNT")
    private Integer factPrdEmpCnt;
    @Column(name="BLD_OWN_GBN_NM")
    private String bldOwnGbnNm;
    @Column(name="SEC_DEP_AMT")
    private Double secDepAmt;
    @Column(name="MNTH_RNT_AMT")
    private Double mnthRntAmt;
    @Column(name="MULTI_USE_GBN_YN")
    private String multiUseGbnYn;
    @Column(name="TOT_FCLTY_AREA")
    private Double totFcltyArea;
    @Column(name="TRADITIONL_INDS_NO")
    private String traditionlIndsNo;
    @Column(name="TRADITIONL_INDS_FOOD")
    private String traditionlIndsFood;
    @Column(name="HOMEPAGE")
    private String homepage;

    @Transient
    private String lastEmptyColumn; //do-nothing

}
