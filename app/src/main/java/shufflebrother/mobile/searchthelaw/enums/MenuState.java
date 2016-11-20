package shufflebrother.mobile.searchthelaw.enums;

/**
 * Created by acer on 2015-02-20.
 */
public enum MenuState {

       MENU_PATENT(0)   // 특허법
     , MENU_MINLAW(1)   // 민법
     , MENU_MINSONGLAW(2) // 민사소송법
     , MENU_MINSA(3)     // 민사집행법
     , MENU_SINAN(4)   // 실용신안법
     , MENU_COPY(5)   // 저작권법
     , MENU_FIGHT(6)  //부정경쟁방지법
     , MENU_TM(7)  // 상표법
     , MENU_DESIGN(8)  // 디자인보호법
     , MENU_HUNLAW(9)  // 헌법
     , MENU_HYUNGLAW(10)  // 형법
     , MENU_SANGLAW(11)  // 상법
     , MENU_HYUNGSALAW(12)  // 형사소송법
     , MENU_HANGSONG(13)  // 행정소송법
     , MENU_SCHOOLLAW(14)  // 학교폭력법
     , MENU_TEACHER(15)// 초중등교육법
     , MENU_TEENAGER(16)  // 청소년보호법
     , MENU_WORKLAW(17)   // 근로기준법
     , MENU_HUNJAEPAN(18)  // 헌법재판소법
     , MENU_COLLATERAL(19)  //가등기담보법
     , MENU_SELFCHECK(20)  // 수표법
     , MENU_PROMISSNOTE(21) // 어음법
     , MENU_GAINTAX(22)      // 소득세법
     , MENU_HERITAGETAX(23)  // 상속세법
     , MENU_FIRMTAX(24)    // 법인세법
     , MENU_VAT(25)       // 부가가치세법
     , MENU_KOREAOFFICE(26)  // 국가공무원법
     , MENU_TEACHOFFICE(27)  // 교육공무원법
     , MENU_ENVIRONMENT(28)  // 자연환경보전법
    , MENU_TRAFFIC(29)  // 도로교통법
    , MENU_SECURITY(30) ; // 정보보호법

     int menuValue ;
     MenuState(int v)
     {
    	menuValue = v;
     }
     int getMenuValue(){
    	 
    	 return menuValue;
     }
}
