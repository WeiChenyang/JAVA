package batchUpload.batchUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class ExcelUtil {
	private static final int DEFAULT_DATA_START_ROW_INDEX = 2;
	//��ȡ��Ԫ������
	public static String getCellvalue(Cell cell){
		String cellValue="";
		if(cell.getType()==CellType.DATE){
			DateCell dc=(DateCell)cell;
			cellValue=FormatUtils.getFormattedDate(dc.getDate());
		}else{
		cellValue = StringUtils
				.trim(cell.getContents());}
		return cellValue;
		
	}
	/**
	 * ���ݵ�Ԫ�����Ը�ʽ����Ԫ������
	 * 
	 * @param cellValue
	 * @param cellType
	 * @return
	 */
	private static String getFormattedCellValue(String cellValue,
			String cellType) {
		if (StringUtils.equalsIgnoreCase(CellType.NUMBER.toString(), cellType
				.toString())) {
			return StringUtils.replace(cellValue, ",", "");
		}
		
		return cellValue;
	}
	/**
	 * ��ȡĬ��������ʼ��
	 * 
	 * @return
	 */
	private static int getDefaultStartRow() {
		String configValue = SystemProperties.get("fas.excel.import.data.start.row");
		if (StringUtils.isNotBlank(configValue)
				&& NumberUtils.isDigits(configValue)) {
			return NumberUtils.createInteger(configValue).intValue();
		}
		return DEFAULT_DATA_START_ROW_INDEX;
	}
	/**
	 * �޸����ñ����С���ȡ��ʵ����
	 * 
	 * @param file
	 *            Excel�ļ�����
	 * @param cls
	 *            ���ɽ��������Ԫ�ض��������
	 * @param dataStartRowIndex
	 *            ��ʵ���ݵ����
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List readExcelPatch(File file, Class cls,
			Integer dataStartRowIndex) {
		// ��ȡʵ�������кţ��������⣬��ȡ��Ч������
		int startRow = dataStartRowIndex != null
				&& dataStartRowIndex.intValue() > 0 ? dataStartRowIndex.intValue() : getDefaultStartRow();
	
		List list = null;
		Workbook wb = null;
		if (file != null) {
			try {
				wb = Workbook.getWorkbook(file);
				if (wb == null) {
					//log.warn("Cannot create WorkBook: " + file.getName());
					return null;
				}
				Sheet[] sheets = wb.getSheets();
				if (sheets == null || sheets.length <= 0) {
					//log.warn("File <" + file.getName() + "> contains zero sheet, it will be ignored.");
					System.out.println("File <" + file.getName() + "> contains zero sheet, it will be ignored.");
					return null;
				}
				// sheet ѭ��
				for (int i = 0; i < sheets.length; i++) {
					Sheet sheet = sheets[i];
					if (sheet != null && sheet.getRows() > 1
							&& sheet.getColumns() > 0) {
						// Find mapping.
						List mappingList = UploadMappingReader.getSheetMapping(
								cls.getName(), "" + (i + 1));
						if (mappingList != null && mappingList.size() > 0) {
							// Clone mapping list
							// List clonedList = new ArrayList();
							// clonedList.addAll(mappingList);
							// Check data in the excel file.
	
							Map colAttrMap = new HashMap();
							// ��ȡ�����е���Ϣ
							for (int colIndex = 0; colIndex < sheet
									.getColumns(); colIndex++) {
								String headerText = sheet.getCell(colIndex,
										startRow - 2).getContents();
								if (StringUtils.isNotBlank(headerText)) {
									// ��ȡÿ�������е�Ԫ�����Ϣ
									for (int listIndex = 0; listIndex < mappingList
											.size(); listIndex++) {
										UploadMappingCell mapCol = (UploadMappingCell) mappingList
												.get(listIndex);
										if (mapCol.getColIndex() != null) {
											// ���ȶ�ȡColIndex�����û����ƥ��header
											if (colIndex == (mapCol
													.getColIndex().intValue() - 1)) {
												colAttrMap.put("" + colIndex,mapCol.getAttribute());
												// clonedList.remove(listIndex);
												break;
											}
										} else if (StringUtils
												.equalsIgnoreCase(StringUtils
														.trim(headerText),
														StringUtils.trim(mapCol
																.getHeader()))) {
											colAttrMap.put("" + colIndex,mapCol.getAttribute());
											// clonedList.remove(listIndex);
											break;
										}
									}
								}
							}
							if (colAttrMap != null && colAttrMap.size() > 0) {
								list = new ArrayList();
								for (int rowIndex = startRow - 1; rowIndex < sheet
										.getRows(); rowIndex++) {
									Object obj = null;
									for (int colIndex = 0; colIndex < sheet
											.getColumns(); colIndex++) {
										Cell cell = sheet.getCell(colIndex,
												rowIndex);
										
										String cellValue=null;
										cellValue=getCellvalue(cell);
										
										if (StringUtils.isNotBlank(cellValue)) {
											CellType cellType = cell.getType();
											// ��ʽ����Ԫ������
											cellValue = getFormattedCellValue(
													cellValue, cellType
															.toString());
											obj = obj != null ? obj : cls
													.newInstance();
											String cellAttribute = (String) colAttrMap.get(colIndex + "");
											/*log.debug("{row: "
															+ rowIndex
															+ ", attribute: "
															+ cellAttribute
															+ ", value: "
															+ cellValue
															+ ", cellType: "
															+ cellType
																	.toString()
															+ "}");*/
											System.out.println("{row: "
															+ rowIndex
															+ ", attribute: "
															+ cellAttribute
															+ ", value: "
															+ cellValue
															+ ", cellType: "
															+ cellType
																	.toString()
															+ "}");
											try {
												BeanUtils.setProperty(obj,
														cellAttribute,
														cellValue);
											} catch (Exception e) {
												/*log.warn("Set value error: "
														+ e);*/
												System.out
														.println("Set value error: "
														+ e);
											}
										}
									}
									if (obj != null) {
										BeanUtils
												.setProperty(
														obj,
														SystemProperties.ATTR_EXCEL_ROW_INDEX,
														rowIndex + "");
										list.add(obj);
									}
								}
							}
						} else {
							/*log.warn("Cannot find mapping define with type '"
									+ cls.getName() + "' and sheet sequence '"
									+ (i + 1) + "'.");*/
							System.out.println("Cannot find mapping define with type '"
									+ cls.getName() + "' and sheet sequence '"
									+ (i + 1) + "'.");
						}
					} else {
						System.out.println("Sheet '" + sheet.getName() + "' of file <"
								+ file.getName() + "> is empty, ignore it.");
						/*log.info("Sheet '" + sheet.getName() + "' of file <"
								+ file.getName() + "> is empty, ignore it.");*/
					}
				}
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}

