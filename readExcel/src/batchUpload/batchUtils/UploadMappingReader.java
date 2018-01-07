package batchUpload.batchUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class UploadMappingReader {

	//private static final Logger log = Logger.getLogger(UploadMappingReader.class);

	private static final String CFG_MAPPING_FILE = "/config/upload-mapping.xml";

	//Map<String, Map<String(seq), Map>>
	//type -> sheet -> List<column>
	private static Map uploadMappingMap = null;

	static{
		//log.debug("Reading upload config mapping.");
		System.out.println("Reading upload config mapping.");
		if(uploadMappingMap == null){
			uploadMappingMap = new HashMap();
			try{
				InputStream in = UploadMappingReader.class.getClassLoader().getResourceAsStream(CFG_MAPPING_FILE);
				SAXReader reader = new SAXReader();
				Document doc = reader.read(in);
				Element rootElement = doc.getRootElement();
				List objectElementList = rootElement.elements("object");
				if(objectElementList!=null && objectElementList.size()>0){
					for(int i = 0; i<objectElementList.size(); i++){
						Element objectElement = (Element) objectElementList.get(i);
						String type = objectElement.attributeValue("type");
						if(StringUtils.isNotBlank(type)){
							//log.debug("Loading mapping of type:" + type);
							System.out.println("Loading mapping of type:" + type);
							List sheetElementList = objectElement.elements("sheet");
							//System.out.println("sheetElementList.size="+sheetElementList.size());
							if(sheetElementList!=null && sheetElementList.size()>0){
								Map objectMap = new HashMap();
								for(int j = 0; j < sheetElementList.size(); j++){
									Element sheetElement = (Element) sheetElementList.get(j);
									String sheetSeq = sheetElement.attributeValue("seq");
									List colElementList = sheetElement.elements("col");
									if(colElementList!=null && colElementList.size()>0){
										List colsList = new ArrayList();
										for(int h = 0; h < colElementList.size() ; h++){
											Element colElement = (Element) colElementList.get(h);
											UploadMappingCell col = new UploadMappingCell();
											col.setHeader(colElement.attributeValue("header"));
											col.setAttribute(colElement.attributeValue("attribute"));
											String colIndex = colElement.attributeValue("colIndex");
											String colWidth = colElement.attributeValue("width");
											col.setCellFormat(colElement.attributeValue("cellFormat"));
											
											String nullStr =  colElement.attributeValue("nullAbled");
											try {
												col.setNullAbled(Boolean.valueOf(nullStr).booleanValue());
											} catch (Exception e) {
												col.setNullAbled(false);
											}
											
											if(StringUtils.isNotBlank(colIndex) && NumberUtils.isDigits(colIndex)){
												col.setColIndex(NumberUtils.createInteger(colIndex));
											}
											if(StringUtils.isNotBlank(colWidth) && NumberUtils.isDigits(colWidth)){
												col.setColWidth(NumberUtils.createInteger(colWidth));
											}
//											log.debug("{header: " + col.getHeader() + ", attribute: " + col.getAttribute() + ", colIndex: " + col.getColIndex() + ",colWidth:"+col.getColWidth()+"seq:"+sheetSeq+"}");
											System.out.println("{header: " + col.getHeader() + ", attribute: " + col.getAttribute() + ", colIndex: " + col.getColIndex() + ",colWidth:"+col.getColWidth()+"seq:"+sheetSeq+"}");
											colsList.add(col);
										}
										objectMap.put(sheetSeq, colsList);
									}
								}
								uploadMappingMap.put(type, objectMap);
							}else{
								//log.error("Element <sheet> is not defined in file <" + CFG_MAPPING_FILE + ">.");
								System.out.println("Element <sheet> is not defined in file <" + CFG_MAPPING_FILE + ">.");
							}
						}else{
							System.out.println("Attribute 'type' is not defined in element <object>.");
							//log.error("Attribute 'type' is not defined in element <object>.");
						}
					}
				}else{
//					log.error("Element <object> is not defined in file <" + CFG_MAPPING_FILE + ">.");
					System.out.println("Element <object> is not defined in file <" + CFG_MAPPING_FILE + ">.");
				}
			}catch(Exception e){
				e.printStackTrace();
//				log.error("Error occured while reading upload mapping file <" + CFG_MAPPING_FILE + ">: " + e.getMessage());
				System.out.println("Error occured while reading upload mapping file <" + CFG_MAPPING_FILE + ">: " + e.getMessage());
			}
//			log.debug("Reading upload config mapping.....[complete].");
			System.out.println("Reading upload config mapping.....[complete].");
		}
	}

	/**
	 * Get All upload mappings.
	 * Each element in the result map is a Map<String, Map> object
	 * @return
	 */
	public static Map getAllMapping(){
		return uploadMappingMap;
	}

	/**
	 * Get upload mapping by type.
	 * Each element in the result map is a Map<String, Map> object.
	 * @param type
	 * @return
	 */
	public static Map getTypeMapping(String type){
		return (Map) uploadMappingMap.get(type);
	}

	/**
	 * Get upload mapping list
	 * Each element in the list is an UploadMappingCell object.
	 * @param type
	 * @param seq
	 * @return
	 */
	public static List getSheetMapping(String type, String seq){
		//printMap();
		Map map = getTypeMapping(type);
		if(map!=null && map.size()>0){
			return (List) map.get(seq);
		}
		return null;
	}

	public static UploadMappingCell getColMapping(String type, String seq, String header){
		List list = getSheetMapping(type, seq);
		if(list!=null && list.size()>0){
			for(int i = 0; i < list.size(); i++){
				UploadMappingCell col = (UploadMappingCell) list.get(i);
				if(header.equalsIgnoreCase(col.getHeader())){
					return col;
				}
			}
		}
		return null;
	}

/*	//´òÓ¡mapÓÃÓÚdebug
	private static void printMap(){
		if(uploadMappingMap!=null && uploadMappingMap.size()>0){
			if(uploadMappingMap!=null && uploadMappingMap.size()>0){
				System.out.println("**************uploadMappingMap begin******");
				for(Iterator it = uploadMappingMap.keySet().iterator();it.hasNext();){
					String key = (String) it.next();
					System.out.println(key + ":" + uploadMappingMap.get(key));
				}
				System.out.println("**************uploadMappingMap end********");
			}
		}else{
			System.out.println("uploadMappingMap is null");
		}
	}*/
	
}
