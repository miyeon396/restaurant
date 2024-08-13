package com.example.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@BatchSize(size = 100)
@Entity
@Table(name = "TBRS_INFO")
public class RestaurantInfo extends CreateBase implements Persistable<Long> {

    @Id
    @Column(name="NO") @Comment("번호")
    private Long no; //"169041",

    @Column(name="OPN_SVC_NM") @Comment("개방서비스명")
    private String opnSvcNm; //"일반음식점",
    @Column(name="OPN_SVC_ID") @Comment("개방서비스아이디")
    private String opnSvcId; //"07_24_04_P",
    @Column(name="OPN_SF_TEAM_CODE") @Comment("개방자치단체코드")
    private String opnSfTeamCode; //"3130000",
    @Column(name="MGT_NO") @Comment("관리번호")
    private String mgtNo; //"3130000-101-2011-00529",
    @Column(name="APV_PERM_YMD") @Comment("인허가일자")
    private String apvPermYmd; //"2011-08-22",
    @Column(name="APV_CANCEL_YMD") @Comment("인허가취소일자")
    private String apvCancelYmd; //"",
    @Column(name="TRD_STATE_GBN") @Comment("영업상태구분코드")
    private String trdStateGbn; //"03",
    @Column(name="TRD_STATE_NM") @Comment("영업상태명")
    private String trdStateNm; //"폐업",
    @Column(name="DTL_STATE_GBN") @Comment("상세영업상태코드")
    private String dtlStateGbn; //"02",
    @Column(name="DTL_STATE_NM") @Comment("상세영업상태명")
    private String dtlStateNm; //"폐업",
    @Column(name="DCB_YMD") @Comment("폐업일자")
    private String dcbYmd; //"2019-03-14",
    @Column(name="CLG_ST_DT") @Comment("휴업시작일자")
    private String clgStdt; //"",
    @Column(name="CLG_END_DT") @Comment("휴업종료일자")
    private String clgEnddt; //"",
    @Column(name="ROPN_YMD") @Comment("재개업일자")
    private String ropnYmd; //"",

    @Column(name="SITE_TEL") @Comment("소재지전화")
    private String siteTel; //"",
    @Column(name="SITE_AREA") @Comment("소재지면적")
    private String siteArea; //"60.60",
    @Column(name="SITE_POST_NO") @Comment("소재지우편번호")
    private String sitePostNo; //"121-130",
    @Column(name="SITE_WHL_ADDR") @Comment("소재지전체주소")
    private String siteWhlAddr; //"서울특별시 마포구 구수동 68-13번지 1층",
    @Column(name="RDN_WHL_ADDR") @Comment("도로명전체주소")
    private String rdnWhlAddr; //"서울특별시 마포구 토정로14길 16 (구수동,1층)",
    @Column(name="RDN_POST_NO") @Comment("도로명우편번호")
    private String rdnPostNo; //"04093",
    @Column(name="BPLC_NM") @Comment("사업장명")
    private String bplcNm; //"수제족발, 구수"",
    @Column(name="LAST_MOD_TS") @Comment("최종수정시점")
    private String lastModTs; //"2019-03-14 16:46:25",

    @Column(name="DATA_UPDATE_GBN") @Comment("데이터갱신구분")
    private String dataUpdateGbn; //"U",
    @Column(name="DATA_UPDATE_DT") @Comment("데이터갱신일자")
    private String dataUpdateDt; //"2019-03-16 02:40:00",

    @Column(name="UPTAE_NM") @Comment("업태구분명")
    private String uptaeNm; //"한식",
    @Column(name="X") @Comment("좌표정보x")
    private String x; //"193959.150482967",
    @Column(name="Y") @Comment("좌표정보y")
    private String y; //"449161.628896747",

    @Column(name="HGY_UPTAE_NM") @Comment("위생업태명")
    private String hgyUptaeNm; //"한식",
    @Column(name="MALE_EMP_CNT") @Comment("남성종사자수")
    private Integer maleEmpCnt; //"",
    @Column(name="FEMALE_EMP_CNT") @Comment("여성종사자수")
    private Integer femaleEmpCnt; //"",
    @Column(name="BIZ_AREA_NM") @Comment("영업장주변구분명")
    private String bizAreaNm; //"",
    @Column(name="GRD_GBN_NM") @Comment("등급구분명")
    private String grdGbnNm; //"",

    @Column(name="WTR_FCLTY_GBN_NM") @Comment("급수시설구분명")
    private String wtrFlctyGbnNm; //"",
    @Column(name="TOT_EMP_CNT") @Comment("총직원수")
    private Integer totEmpCnt; //"",
    @Column(name="HQ_EMP_CNT") @Comment("본사직원수")
    private Integer hqEmpCnt; //"",
    @Column(name="FACT_OFC_EMP_CNT") @Comment("공장사무직직원수")
    private Integer fctyOfcEmpCnt; //"",
    @Column(name="FACT_SLS_EMP_CNT") @Comment("공장판매직직원수")
    private Integer factSlsEmpCnt; //"",
    @Column(name="FACT_PRD_EMP_CNT") @Comment("공장생산직직원수")
    private Integer factPrdEmpCnt; //"",
    @Column(name="BLD_OWN_GBN_NM") @Comment("건물소유구분명")
    private String bldOwnGbnNm; //"",

    @Column(name="SEC_DEP_AMT") @Comment("보증액")
    private Double secDepAmt; //"",
    @Column(name="MNTH_RNT_AMT") @Comment("월세액")
    private Double mnthRntAmt; //"",
    @Column(name="MULTI_USE_GBN_YN") @Comment("다중이용업소여부")
    private String multiUseGbnYn; //"N",
    @Column(name="TOT_FCLTY_AREA") @Comment("시설총규모")
    private String totFcltyArea; //"60.6",
    @Column(name="TRADITIONL_INDS_NO") @Comment("전통업소지정번호")
    private String traditionlIndsNo; //"",
    @Column(name="TRADITIONL_INDS_FOOD") @Comment("전통업소주된음식")
    private String traditionlIndsFood; //"",
    @Column(name="HOMEPAGE") @Comment("홈페이지")
    private String homepage; //"",

    @Transient
    private String lastEmptyColumn; //do-nothing


    @Override
    public Long getId() {
        return no;
    }

    @Override
    public boolean isNew() {
        return getCreaDt() == null;
    }
}
