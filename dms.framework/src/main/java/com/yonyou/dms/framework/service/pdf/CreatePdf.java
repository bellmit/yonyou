package com.yonyou.dms.framework.service.pdf;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.OemDictCodeConstants;



public class CreatePdf {
	 Document document = new Document();// 建立一个Document对象     
     private static Font headfont ;// 设置字体大小 
     private static Font thfont;// 设置字体大小 
     private static Font tdfont;// 设置字体大小 
     private static Font spfont;// 设置字体大小 
     private static Font font;
     int maxWidth = 540;
     float rightWidth1= 112;
     float rightWidth2 =135;
     float rightWidth3 =210;
     float leftWidth1=320;
     float leftWidth2=282;
     
      
     static{ 
         BaseFont baseFont; 
         try {
        	 baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);  
        	 font = new Font(baseFont, 20, Font.UNDERLINE|Font.NORMAL|Font.BOLD);
             headfont = new Font(baseFont, 15, Font.BOLD);// 设置字体大小 
             thfont = new Font(baseFont, (float) 9.5, Font.NORMAL);// 设置字体大小 
             tdfont = new Font(baseFont, (float) 9.5, Font.NORMAL);// 设置字体大小 
             spfont = new Font(baseFont, (float) 1.5, Font.NORMAL);// 设置字体大小 
         } catch (Exception e) {          
             e.printStackTrace(); 
         }  
     } 
     
     public CreatePdf(File file) {         
          document.setPageSize(PageSize.A4);// 设置页面大小
          try { 
        	  PdfWriter.getInstance(document,new FileOutputStream(file)); 
              document.open(); 
          } catch (Exception e) { 
              e.printStackTrace(); 
          }
		
     } 
     
         
     //头 cell
     public PdfPCell  headCell(String value){ 
    	 PdfPCell cell = new PdfPCell(); 
    	 cell.setColspan(6);
    	 cell.setPhrase(new Phrase(value,headfont)); 
    	 cell.setHorizontalAlignment(Element.ALIGN_LEFT);  
    	 return cell ;
     }
     
     //Th cell
     public PdfPCell  thCell(String value){ 
    	return  thCell(value,null);
     }
     //Th cell
     public PdfPCell  thCell(String value,Integer cospan){ 
    	return thCell(value, cospan, null);
     }
     //Th cell
     public PdfPCell  thCell(String value,Integer cospan,Integer alin){ 
    	 PdfPCell cell = new PdfPCell(); 
    	 cell.setColspan(cospan==null?1:cospan);
    	 cell.setPaddingTop(7);
    	 cell.setPaddingBottom(4);
    	 cell.setPhrase(new Phrase(value,thfont));
    	 cell.setHorizontalAlignment(alin==null?Element.ALIGN_CENTER:alin);
    	 return cell ;
     }
     //Th cell
     public PdfPCell  thCell(String value,String sp,Integer rowspan,Integer colspan){ 
    	 PdfPCell cell = new PdfPCell();
         cell.setRowspan(rowspan);
         cell.setColspan(colspan);
    	 cell.setPaddingTop(2);
    	 cell.setPaddingBottom(4);
    	 cell.setPhrase(new Phrase(value,thfont));
    	 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	 return cell ;
     }
   //sp cell
     public PdfPCell  spCell(String value){ 
    	 PdfPCell cell = new PdfPCell(); 
    	 cell.setPhrase(new Phrase(value,spfont));
    	 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	 return cell ;
     }
     //td cell
     public PdfPCell  tdCell(String value)
     { 
    	return tdCell (value,null);
     }
     
   //td cell 
     public PdfPCell  tdCell(String value , Integer cospan,Integer rowspan,Integer border){
    	 PdfPCell cell = new PdfPCell(); 
    	 cell.setColspan(cospan==null?1:cospan);
    	 cell.setRowspan(rowspan);
    	 cell.setPaddingBottom(5);
    	 cell.setBorder(border);
    	 cell.setPhrase(new Phrase(value,tdfont)); 
    	 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    	 return cell ;
     } 
   //td cell
     public PdfPCell  tdCell(String value,Integer cospan,Integer rowspan,Integer n,Integer tPadding,Integer bPadding){//n=0 居中 ;n=1 靠左  ;其他靠右
    	 PdfPCell cell = new PdfPCell(); 
    	 cell.setColspan(cospan==null?1:cospan);
    	 cell.setRowspan(rowspan);
    	 cell.setPaddingTop(tPadding);
    	 cell.setPaddingBottom(bPadding);
    	 cell.setPhrase(new Phrase(value,tdfont));
    	 if(n.equals(0)){
    		 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    		 return cell;
    	 }
    	 if(n.equals(1)){
    		 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    		 return cell;
    	 }
    	 cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	 return cell ;
     } 
   //td cell
     public PdfPCell  tdCell(Chunk chunk){
    	PdfPCell cell = new PdfPCell(); 
    	cell.addElement(chunk);
    	cell.setPaddingLeft(30);
    	cell.setPaddingBottom(20);
    	cell.setPaddingRight(30);
    	cell.setPaddingTop(10);
    	cell.setBorder(0);
		return cell;
    	
     } 
    //td cell
     public PdfPCell  tdCell(String value , Integer cospan){ 
    	 PdfPCell cell = new PdfPCell(); 
    	 cell.setColspan(cospan==null?1:cospan);
    	 cell.setPaddingTop(7);
    	 cell.setPaddingBottom(5);
    	 cell.setPhrase(new Phrase(value,tdfont)); 
    	 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    	 return cell ;
     }
   //td cell
     public PdfPCell  tdCell(Integer border , Integer cospan,Integer padding){ 
    	 PdfPCell cell = new PdfPCell(); 
    	 cell.setBorder(border);
    	 cell.setPadding(padding);
    	 cell.setColspan(cospan==null?1:cospan);
    	 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	 return cell ;
     }
     
     
     public PdfPTable createTable(Integer col){
    	 return  createTable(col,null );
     }
     public PdfPTable createTable(Integer col,Integer widthPercent ){ 
         PdfPTable table = new PdfPTable(col); 
         table.setWidthPercentage( widthPercent==null? 100:widthPercent );   
         return table; 
    } 
     
     public PdfPCell createCell(String value,Font keyfont2,int align){ 
         PdfPCell cell = new PdfPCell(); 
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);         
         cell.setHorizontalAlignment(align);     
         cell.setPhrase(new Phrase(value,keyfont2)); 
        return cell; 
    } 
     
      
      public PdfPCell createCell(String value,Font font){ 
          PdfPCell cell = new PdfPCell(); 
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);  
          cell.setPhrase(new Phrase(value,font)); 
         return cell; 
     } 
  
      public PdfPCell createCell(String value,Font font,int align,int colspan){ 
          PdfPCell cell = new PdfPCell(); 
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
          cell.setHorizontalAlignment(align);     
          cell.setColspan(colspan); 
          cell.setPhrase(new Phrase(value,font)); 
         return cell; 
     } 
     public PdfPCell createCell(String value,Font font,int align,int colspan,boolean boderFlag){ 
          PdfPCell cell = new PdfPCell(); 
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
          cell.setHorizontalAlignment(align);     
          cell.setColspan(colspan); 
          cell.setPhrase(new Phrase(value,font)); 
          cell.setPadding(3.0f); 
          if(!boderFlag){ 
              cell.setBorder(0); 
              cell.setPaddingTop(15.0f); 
              cell.setPaddingBottom(8.0f); 
          } 
         return cell; 
     } 
     public PdfPCell createCell(PdfPTable table,boolean boderFlag){ 
         PdfPCell cell = new PdfPCell(); 
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
         cell.setBorder(0);
         cell.setPadding(3.0f); 
         if(!boderFlag){ 
             cell.setBorder(0); 
         } 
        return cell; 
    } 
     public PdfPTable createTable(int colNumber){ 
         PdfPTable table = new PdfPTable(colNumber); 
         try{ 
             table.setTotalWidth(maxWidth); 
             table.setLockedWidth(true); 
             table.setHorizontalAlignment(Element.ALIGN_CENTER);      
             table.getDefaultCell().setBorder(0); 
         }catch(Exception e){ 
             e.printStackTrace(); 
         } 
         return table; 
     } 
      public PdfPTable createTable1(int colNumber,float width){ 
         PdfPTable table = new PdfPTable(colNumber); 
         try{ 
             table.setTotalWidth(width); 
             table.setLockedWidth(true); 
             table.setHorizontalAlignment(Element.ALIGN_LEFT);      
             table.getDefaultCell().setBorder(0); 
         }catch(Exception e){ 
             e.printStackTrace(); 
         } 
         return table; 
     } 
      public PdfPTable createTable2(int colNumber){ 
          PdfPTable table = new PdfPTable(colNumber); 
          try{ 
              table.setTotalWidth(maxWidth-4); 
              table.setLockedWidth(true); 
              table.setHorizontalAlignment(Element.ALIGN_CENTER);      
              table.getDefaultCell().setBorder(1); 
          }catch(Exception e){ 
              e.printStackTrace(); 
          } 
          return table; 
      } 
      public PdfPTable createTable3(int colNumber,float width){ 
          PdfPTable table = new PdfPTable(colNumber); 
          try{ 
              table.setTotalWidth(width); 
              table.setLockedWidth(true); 
              table.setHorizontalAlignment(Element.ALIGN_RIGHT); 
              table.getDefaultCell().setBorder(1);
          }catch(Exception e){ 
              e.printStackTrace(); 
          } 
          return table; 
      } 
      public PdfPTable createTable(float[] widths){ 
             PdfPTable table = new PdfPTable(widths); 
             try{ 
                 table.setTotalWidth(maxWidth); 
                 table.setLockedWidth(true); 
                 table.setHorizontalAlignment(Element.ALIGN_CENTER);      
                 table.getDefaultCell().setBorder(1); 
             }catch(Exception e){ 
                 e.printStackTrace(); 
             } 
             return table; 
         } 
      
      public PdfPTable createBlankTable(){ 
          PdfPTable table = new PdfPTable(1); 
          table.getDefaultCell().setBorder(0); 
          table.addCell(createCell("", thfont));          
          table.setSpacingAfter(20.0f); 
          table.setSpacingBefore(20.0f);
          return table; 
      } 
      
      public void  generatePDF(Map<String, Object> claimInfoMap,Map<String, Object> claimDetailInfoMap,String contextPath) throws Exception{ 
            
    	 Chunk chunk1 = new Chunk("配件索赔报告书       \n\n",font);
    	 PdfPTable table1 = createTable1(8,leftWidth1);
         table1.addCell(tdCell("经销商名称："+Utility.checkNull(claimInfoMap.get("DEALER_NAME")),5));
         table1.addCell(tdCell("联系电话："+Utility.checkNull(claimInfoMap.get("LINK_MAN_MOBILE")),3));
         table1.addCell(tdCell("经销商代码："+Utility.checkNull(claimInfoMap.get("FCA_CODE")),5));
         table1.addCell(tdCell("传真号码："+Utility.checkNull(claimInfoMap.get("FAX_NO")),3));
         table1.addCell(tdCell("经销商索赔负责人："+Utility.checkNull(claimInfoMap.get("LINK_MAN")),8));
         
         PdfPTable table2=createTable2(9);
         table2.addCell(tdCell("索赔报告书编号："+Utility.checkNull(claimInfoMap.get("CLAIM_NO")),3));
         table2.addCell(tdCell("运输公司："+Utility.checkNull(claimDetailInfoMap.get("TRANS_COMPANY")),2));
         table2.addCell(tdCell("运单编号："+Utility.checkNull(claimDetailInfoMap.get("TRANS_NO")),2));
         table2.addCell(tdCell("箱号："+Utility.checkNull(claimDetailInfoMap.get("BOX_NO")),2));
         table2.addCell(tdCell("申请日期："+Utility.checkNull(claimInfoMap.get("CLAIM_DATE")),3));
         table2.addCell(tdCell("运输方式："+Utility.checkNull(claimDetailInfoMap.get("TRANS_TYPE")),2));
     	 table2.addCell(tdCell("到店日期："+Utility.checkNull(claimDetailInfoMap.get("ARRIVED_DATE")),2));
 		 table2.addCell(tdCell("开箱检验日期："+Utility.checkNull(claimDetailInfoMap.get("CHECKED_DATE")),2));
 		 
 		 PdfPTable table3 = createTable1(11,leftWidth1);
     	 table3.addCell(thCell("项目序号",1));
     	 table3.addCell(thCell("交货单号",2));
     	 table3.addCell(thCell("零部件编号",2));
     	 table3.addCell(thCell("零部件名称",3));
     	 table3.addCell(thCell("交货单明细单价RMB(不含税)",3));
     	 table3.addCell(thCell("1",1));
     	 table3.addCell(thCell(Utility.checkNull(claimDetailInfoMap.get("DELIVER_NO")),2));
		 table3.addCell(thCell(Utility.checkNull(claimDetailInfoMap.get("PART_CODE")),2));
		 table3.addCell(thCell(Utility.checkNull(claimDetailInfoMap.get("PART_NAME")),3));
		 table3.addCell(thCell(Utility.checkNull(claimDetailInfoMap.get("PART_PRICE")),3));
		 table3.addCell(tdCell(0,0,0));
		 table3.addCell(tdCell(0,3,0));
		 table3.addCell(tdCell(0,3,0));
		 table3.addCell(tdCell(0,3,0));
		 table3.addCell(tdCell(0,3,0));

 		 PdfPTable table4 = createTable3(7,rightWidth3);
     	 table4.addCell(thCell("索赔代码","",2,1));
     	 table4.addCell(thCell("\n数量","",2,1));
     	 table4.addCell(thCell("索赔","",1,2));
     	 table4.addCell(thCell("\n误送零部件编号","",2,3));
     	 table4.addCell(thCell("总价RMB","",1,2));
     	 String claimCode=Utility.checkNull(claimInfoMap.get("CLAIM_PROPERTY"));
     	 if(claimCode.equals("80071001")){
     		 claimCode="A";
     	 }else if(claimCode.equals("80071002")){
     		 claimCode="B";
     	 }else if(claimCode.equals("80071003")){
     		 claimCode="C";
     	 }else if(claimCode.equals("80071004")){
     		 claimCode="D";
     	 }else if(claimCode.equals("80071005")){
     		 claimCode="E";
     	 }else if(claimCode.equals("80071006")){
     		 claimCode="F";
     	 }else if(claimCode.equals("80071007")){
     		 claimCode="G";
     	 }else if(claimCode.equals("80071008")){
     		 claimCode="H";
     	 }else if(claimCode.equals("80071009")){
     		 claimCode="I";
     	 }
     	 table4.addCell(thCell(claimCode));
     	 table4.addCell(thCell(Utility.checkNull(claimDetailInfoMap.get("CLAIM_NUM")) ));
		 table4.addCell(thCell(Utility.checkNull(claimDetailInfoMap.get("AMOUNT")) ,2));
		 table4.addCell(thCell(Utility.checkNull(claimDetailInfoMap.get("DELIVERY_ERROR_PART")) ,3));
		 table4.addCell(tdCell(0,0,5));
		 table4.addCell(tdCell(0,0,5));
		 table4.addCell(tdCell("合计:  "+Utility.checkNull(claimDetailInfoMap.get("AMOUNT")),2,1,1,7,5));
		 table4.addCell(tdCell(0,3,5));
	     
 		 PdfPTable table5 = createTable1(9,leftWidth2);//第五行表   左
 		 table5.addCell(tdCell("索赔代码",1,1,0,15,10));
    	 table5.addCell(tdCell("种类",3,1,0,15,10));
    	 table5.addCell(tdCell("说明：\n\n" 
    			  +	"1.左表中“不足或过量”是指收到货物数量与随货装箱明细不符。\n\n" 
    			  + "2.左表中“系统原因导致错件”是指因为查询件号系统锁定错误或者DMS系统传输错误，导致所订配件与所需配件不符。\n\n"
    			  + "3.左表中“特别批准 ”是指区域经理特别批准的索赔。\n\n"
    			  + "4.“其他”主要指除左表中列出的情况之外的一切特殊情况。\n\n",5,10,1,0,0));
    	 
    	 table5.addCell(tdCell("A",1,1,0,16,10));
    	 table5.addCell(tdCell("零件破损（变形、划花、破裂）",3,1,0,12,10));
     	 table5.addCell(tdCell("B",1,1,0,16,10));
     	 table5.addCell(tdCell("实物与标签不符合",3,1,0,16,10));
    	 table5.addCell(tdCell("C",1,1,0,16,10));
    	 table5.addCell(tdCell("不完全组件",3,1,0,16,10));
     	 table5.addCell(tdCell("D",1,1,0,16,10));
     	 table5.addCell(tdCell("不足或过量",3,1,0,16,10));
    	 table5.addCell(tdCell("E",1,1,0,16,10));
    	 table5.addCell(tdCell("系统原因导致错误",3,1,0,16,10));
     	 table5.addCell(tdCell("F",1,1,0,16,10));
     	 table5.addCell(tdCell("零件生锈",3,1,0,16,10));
    	 table5.addCell(tdCell("G",1,1,0,16,10));
     	 table5.addCell(tdCell("钥匙配置错误",3,1,0,16,10));
    	 table5.addCell(tdCell("H",1,1,0,16,10));
    	 table5.addCell(tdCell("特别批准",3,1,0,16,10));
     	 table5.addCell(tdCell("I",1,1,0,16,10));
     	 table5.addCell(tdCell("其他",3,1,0,16,10));
     	 
     	 PdfPTable table6 = createTable3(1,rightWidth1);//第五行表    中 
         table6.addCell(tdCell("经销商详细说明及要求：\n\n\n\n\n" +
         		Utility.checkNull(claimDetailInfoMap.get("CLAIM_OPTIONS")) ));
         
         PdfPTable table7 = createTable3(1,rightWidth2);//第五行表    右1  
//         table7.addCell(tdCell("GACFIAT索赔员审核意见及说明：",1,1,1,0,3));
//         table7.addCell(tdCell( 
//    			 CodeDict.getDictDescById(Constant.PART_CLAIM_STATUS.toString(), CommonUtils.checkNull(claimDetailInfoMap.get("CLAIM_STATUS")))
//    			   +" ;\n"+Utility.checkNull(claimDetailInfoMap.get("AUDIT_REMARK"))
//        		   +" \n\n\n              签字：___________\n\n",4));
         table7.addCell(tdCell("索赔员审核意见及说明：\n"
        		 +getDictDescById(OemDictCodeConstants.PART_CLAIM_STATUS.toString(), Utility.checkNull(claimDetailInfoMap.get("CLAIM_STATUS")))
        				 +" ;\n"+Utility.checkNull(claimDetailInfoMap.get("AUDIT_REMARK"))
        				 +"\n\n\n                     签字：___________\n\n"));
         PdfPTable table8 = createTable3(1,rightWidth2);//第五行表    右2
         String auditOptions=Utility.checkNull(claimDetailInfoMap.get("AUDIT_OPTIONS"));
         auditOptions="  □ 退货        □ 换货\n\n";
//         if(auditOptions.equals("")){
//        	 auditOptions=" □ 返厂更换    □ 店方自行维修\n\n"+
//                     " □ 封存待集中处理      □ 退货\n\n"+
//                     " □ 补发  \n\n";
//         }else{
//             if(auditOptions.equals("80091001")){
//            	 auditOptions=" ■ 返厂更换    □ 店方自行维修\n\n"+
//    		              " □ 封存待集中处理      □ 退货\n\n"+
//    		              " □ 补发  \n\n";
//             }else if(auditOptions.equals("80091002")){
//            	 auditOptions=" □ 返厂更换   ■ 店方自行维修\n\n"+
//	                      " □ 封存待集中处理      □ 退货\n\n"+
//	                      " □ 补发  \n\n";
//             }else if(auditOptions.equals("80091003")){
//            	 auditOptions=" □ 返厂更换    □ 店方自行维修\n\n"+
//	                      " ■ 封存待集中处理      □ 退货\n\n"+
//	                      " □ 补发  \n\n";
//             }else if(auditOptions.equals("80091004")){
//            	 auditOptions=" □ 返厂更换    □ 店方自行维修\n\n"+
//	                      " □ 封存待集中处理     ■ 退货\n\n"+
//	                      " □ 补发  \n\n";
//             }else if(auditOptions.equals("80091005")){
//            	 auditOptions=" □ 返厂更换    □ 店方自行维修\n\n"+
//	                      " □ 封存待集中处理      □ 退货\n\n"+
//	                      " ■ 补发  \n\n";
//             }
//         }
       
         table8.addCell(tdCell("对索赔物品本体的处理意见：\n\n"+auditOptions));
         
         PdfPTable table9 = createTable3(1,rightWidth2);//第五行表    右3
         table9.addCell(tdCell("相关人员解释说明：\n\n\n\n\n\n\n                     签字：___________\n\n"));
         
         PdfPTable table10 = createTable3(1,rightWidth2);//第五行表    右4
         table10.addCell(tdCell("配件供应负责人处理意见：\n\n\n\n\n\n\n                     签字：___________\n\n"));
         
         PdfPTable tablet=createTable3(1, rightWidth2);//第五行表    右总
         PdfPCell tablet1=new PdfPCell(table7);
         tablet1.setBorder(0);
         PdfPCell tablet2=new PdfPCell(table8);
         tablet2.setBorder(0);
         PdfPCell tablet3=new PdfPCell(table9);
         tablet3.setBorder(0);
         PdfPCell tablet4=new PdfPCell(table10);
         tablet4.setBorder(0);
         
         PdfPCell spaceCell=new PdfPCell(spCell(" "));//空表
         spaceCell.setBorder(0);
         tablet.addCell(tablet1);
         tablet.addCell(spaceCell);
         tablet.addCell(tablet2);
         tablet.addCell(spaceCell);
         tablet.addCell(tablet3);
         tablet.addCell(spaceCell);
         tablet.addCell(tablet4);
         
         PdfPTable t=createTable(1);//总表
		 
 		 PdfPTable t1=createTable(2);//第一行表    
 		 PdfPTable t11=createTable1(1,leftWidth1);//左表
         t11.addCell(tdCell(chunk1));//左上
         t11.addCell(table1);//左下
         t1.addCell(t11);
         PdfPTable t12=createTable3(1, rightWidth3);
         PdfPTable tableR=createTable1(3,rightWidth3);//图片表
        
         //获取当前项目的绝对路径（Linux不适用） 
         String path= this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(); 
		 int index = path.lastIndexOf("WEB-INF/lib/");
		 path = path.substring(0, index);
         String imagePath1=path+"images/pdfReport/GACFIAT.png";
         String imagePath2=path+"images/pdfReport/FIAT.png";
         //图片   
		try {
			 Image image1 = Image.getInstance(imagePath1);
	         Image image2 = Image.getInstance(imagePath2);
	         PdfPCell pi1=new PdfPCell(image1);
	         pi1.setColspan(3);
	         pi1.setPaddingTop(20);
	         pi1.setBorder(0);
	         PdfPCell pi2=new PdfPCell(image2);
	         pi2.setColspan(3);
	         pi2.setPaddingTop(0);
	         pi2.setBorder(0);
	         tableR.addCell(pi1);
	         tableR.addCell(pi2);
		} catch (BadElementException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
         PdfPCell tableRU=new PdfPCell(tableR);
         tableRU.setBorder(0);
         t12.addCell(tableRU);
         
         PdfPTable tableRD=createTable3(1,rightWidth3-10);//图片下表
         tableRD.addCell(tdCell(
         		"地                   址:   上海市闵行区申长路\n                                 虹桥万科中心988号\n" +
         		"联系电话(Tel):   0731-89989772\n" +
         		"传         真(Fax):   0731-89989772\n" +
         		"邮        编(Post):   410100",1,1,0));
         PdfPCell tableRd=new PdfPCell(tableRD);
         tableRd.setBorder(0);
         
         t12.addCell(tableRd);
         t1.addCell(t12);
         
         PdfPTable t3=createTable(2); //第三行表        
         t3.addCell(table3);//左
         t3.addCell(table4);//右
         
         PdfPTable t4=createTable2(20);//第四行表   
         t4.addCell(tdCell("下表为到货后情况判定说明代码",15,10,0));
         t4.addCell(tdCell("以下由GACFCA填写",5,10,0));
         
         
         PdfPTable t5=createTable(23); //第五行表        
         PdfPCell cell5 = new PdfPCell(table5);//左
         cell5.setColspan(12);
         cell5.setBorder(0);
         t5.addCell(cell5);
         PdfPCell cell6 = new PdfPCell(table6);//中
         cell6.setColspan(5);
         cell6.setBorder(0);
         t5.addCell(cell6);
         PdfPCell cell7 = new PdfPCell(tablet);//右
         cell7.setColspan(6);
         cell7.setBorder(0);
         t5.addCell(cell7);
         
         t.addCell(t1);
         PdfPCell p2=new PdfPCell(table2);//第二行表
         p2.setBorder(0);
         t.addCell(p2);
         t.addCell(spaceCell);
         PdfPCell p3=new PdfPCell(t3);
         p3.setBorder(0);
         t.addCell(p3);
         PdfPCell p4=new PdfPCell(t4);
         p4.setBorder(0);
         t.addCell(p4);
         PdfPCell p5=new PdfPCell(t5);
         p5.setBorder(0);
         t.addCell(p5);
         
         document.add(t);
         
         document.close(); 
      }

	/**
	 * 根据数据字典类型和数据描述，返回数据字典的codeID
	 * @param string
	 * @param checkNull
	 * @return
	 */
	private String getDictDescById(String type, String id) {
		
		String sql = "SELECT * FROM TC_CODE_DCS WHERE CODE_ID = "+id+"";
		@SuppressWarnings("unchecked")
		Map<String,Object> map = OemDAOUtil.findFirst(sql, null);
		String codeDesc = "";

		if(map!=null){
			codeDesc = (String) map.get("CODE_DESC");

		}
				
		return codeDesc;
	}
      
}
