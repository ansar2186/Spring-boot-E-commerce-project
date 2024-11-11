 $(document).ready(function(){
            $('#buttonCancel').on("click",function(){
                window.location=moduleURL;
            });
            $('#fileImage').change(function(){
            fileSize = this.files[0].size;
            alert("File size : " +fileSize);
            if(fileSize > 1048576){
            this.setCustomValidity("you must choose an image less than 1 MB");
            this.reportValidity();
            }else{
            this.setCustomValidity("");
            showImageThumbnail(this);
             }
            });

        });

        function showImageThumbnail(fileInput){
        var file=fileInput.files[0];
        var reader = new FileReader();
        reader.onload=function(e){
        $('#thumbnail').attr("src",e.target.result);
        };
        reader.readAsDataURL(file);
        }

    $(document).ready(function(){
     $('#logoutLink').on("click",function(e){
       e.preventDefault();
       document.logoutForm.submit();
     });
    });

   function customizeDropDownMenu() {
	$(".dropdown > a").click(function() {
		location.href = this.href;
	});
}
        function checkPasswordMatch(){

        if(confirmPassword.value != $("#password").val()){
        <!-- alert("Password do not match !!");-->
         confirmPassword.setCustomValidity("Password do not match !!");
        }else{
        confirmPassword.setCustomValidity("");
        }
	}