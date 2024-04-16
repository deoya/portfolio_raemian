window.onload = function(){ 
	var ck = CKEDITOR.replace("incontent",{
		height:395
	});
}

var html = new Vue({
	el:"#vueBox",
	data:{
		maxSize :  2097152, //2MB
		msg : "",
		filecked : "yes",
	},
	methods:{
		fileck : function(data){
			// 파일 확장자 제한
			var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|pdf)$");
			if(data.target.files[0] != null) {
				if(data.target.files[0].size > this.maxSize){
					this.msg = "파일은 2MB 이하만 등록이 가능합니다"
					this.filecked = "no"
				}
				else if(regex.test(data.target.files[0].name)){
					this.msg = "해당 파일은 등록이 불가능 합니다"
					this.filecked = "no"
				}else {
					this.filecked = "yes"
				}	
			}
		},
		insert:function(){
			var ck = CKEDITOR.instances.incontent.getData();
			if(f.ntitle.value == "" || ck == ""){
				alert("내용을 채워주세요")
			}else if(this.filecked == "no"){
				alert(this.msg)
			}else {
				f.action=path+"/raemian_admin/notice/insert"
				f.method="post"
				f.enctype="multipart/form-data"
				f.submit();
				
			}
		}
	}
})