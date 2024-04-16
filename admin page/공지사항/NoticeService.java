package folio.port.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import folio.port.domain.infoDAO;
import folio.port.domain.memberDAO;
import folio.port.domain.noticeDAO;
import folio.port.mapper.NoticeMapper;
import folio.port.module.module1;
import folio.port.module.module_cdn;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class NoticeService {
	module1 module1 = new module1();
	module_cdn<noticeDAO> cdn = null;
	
	@Setter(onMethod_= @Autowired)
	private NoticeMapper mapper;
	
	//--[데이터 출력]------------------
	public List<noticeDAO> getList(){
		return mapper.getList();
	}
	public List<noticeDAO> select_pg(int page) {
 		return mapper.select_page(page);
	}
	// 데이터 검색
	public List<noticeDAO> select_search(String search) {
		return mapper.select_search(search);
	}
	// 해당 데이터의 페이지
	public List<noticeDAO> select_search_pg(int page, String search) {
		Map<String, Object> sh = new HashMap<>();
	    sh.put("page", page);
	    sh.put("search", search);
	    return mapper.select_search_pg(sh);
	}
	
	
	//--[글 등록]------------------
	public String insert(noticeDAO dao, MultipartFile file) {
		if(!file.isEmpty()) { // 파일첨부가 있을 경우
			this.cdn = new module_cdn<>();
			dao.setNfile_name(file.getOriginalFilename());
			
			UUID uuid = UUID.randomUUID();
			
			String upfilename =  uuid.toString()+"_"+ file.getOriginalFilename();
			dao.setNfile(upfilename);
		
			if(cdn.cdn(upfilename, file, dao) == true) {
	        	if(mapper.insert(dao) > 0) {
	        		return module1.move("등록을 완료 하였습니다","/r/raemian_admin/notice/notice_main");	
	        	}else {
	        		cdn.cdn_delete(dao.getUploadPath(), dao.getNfile());
	        		return module1.back("등록에 실패하였습니다 [번호 : 015]");
	        	}			
			}else {
				return module1.back("등록에 실패하였습니다 [번호 : 014]");
			}
		}else { //파일첨부가 없을 경우
			dao.setNfile("");
			dao.setUploadPath("");
			if(mapper.insert(dao) > 0) {
        		return module1.move("등록을 완료 하였습니다","/r/raemian_admin/notice/notice_main");	
        	}else {
        		return module1.back("등록에 실패하였습니다 [번호 : 015]");
        	}
		}
	}
	//--[글 삭제]------------------
	public String delete(int nidx, String nfile, String path) {
		if(mapper.delete(nidx) > 0) {
			if(!nfile.isEmpty()) {
				this.cdn = new module_cdn<>();
				cdn.cdn_delete(path, nfile);
			}
			return "삭제가 완료되었습니다";
		}else return "삭제에 실패하였습니다 [번호 :017]";
	}
	
	//--[단일 데이터 출력]------------------
	public noticeDAO get(int nidx) {
		return mapper.get(nidx);
	}
}
