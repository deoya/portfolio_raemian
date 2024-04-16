package folio.port.domain;

import java.util.Date;

import lombok.Data;

@Data
public class noticeDAO implements UploadPathSettable{
	private int nidx,aidx,ncnt;
	
	private String ntitle,aname,ncontent,nfile,uploadPath,nfile_name;
	
	private Date ndate, nupdate;
	
	@Override
    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
	
}
