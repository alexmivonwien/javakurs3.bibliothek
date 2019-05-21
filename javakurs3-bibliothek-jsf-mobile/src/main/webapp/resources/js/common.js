 
/** Zum Datei Upload mit HTML5 sieh https://wiki.selfhtml.org/wiki/JavaScript/File_Upload#Auswahl_mit_Drag_und_Drop **/

var fileList = []; // an array of objects representing all the files in the HTML table and a comment to each file
      
/**
 * 
 * @sieh https://wiki.selfhtml.org/wiki/JavaScript/File_Upload#Auswahl_mit_Drag_und_Drop
 * 
 * 
 * @param evt
 * @returns
 */
function dateiauswahl(evt) {
	evt.stopPropagation();
	evt.preventDefault();
	var gewaehlteDateien = evt.dataTransfer.files;
	// FileList Objekt
	var output = [];
	var selectBox = document.getElementById('flist');
	for (var i = 0, f; f = gewaehlteDateien[i]; i++) {
		fileList.push(f);
		var NeuerEintrag = new Option(f.name + f.size + ' bytes', f, false,
				true);
		selectBox.options[selectBox.length] = NeuerEintrag;
		addArrayElement(output, f);
	}

	if (gewaehlteDateien.length > 0) {
		selectBox.selectedIndex = 0;
		previewFile();
	}
}

function addArrayElement(output, f) {
	output.push('<li><strong>', escape(f.name), '</strong> (', f.type || 'n/a',
			') - ', f.size, ' bytes, last modified: ', f.lastModifiedDate
					.toLocaleDateString(), '</li>');
}

function printFileDetails(output) {
	document.getElementById('list').innerHTML = '<ul>' + output.join('')
			+ '</ul>';
}

/**
 * @see https://stackoverflow.com/questions/42348917/drag-and-drop-input-file
 * 
 * @returns
 */
function previewFile() {

	var selectBox = document.getElementById('flist');
	var f = fileList[selectBox.selectedIndex];

	console.log(f);
	console.log(f.size);

	if (!f) {
		return;
	}
	var output = [];
	addArrayElement(output, f);
	printFileDetails(output);

	var reader = new FileReader();

	if (f && f.type.match('image.*')) {
		reader.addEventListener("load", function() {
			var pview = document.getElementById('libraryImageId');
			pview.src = reader.result;
			console.log('writing stuff');
		}, false);
		reader.readAsDataURL(f);
	}
}

function handleDragOver(evt) {
	evt.stopPropagation();
	evt.preventDefault();
	evt.dataTransfer.dropEffect = 'copy';
}

function init() {
	var dropZone = document.getElementById('dropzone');
	dropZone.addEventListener('dragover', handleDragOver, false);
	dropZone.addEventListener('drop', dateiauswahl, false);
}


function ajaxUploadFile() {
	var form = document.getElementById('uploadImageForNewCustomerformId');
	
	if (form == null)
		return;
	
     var formData = new FormData(form);

     for (var i = 0; i < fileList.length; i ++){
       //append a File to the FormData object
       formData.append("file", fileList[i], fileList[i].name);
     }
    
    var uploadStatusOutput = document.getElementById("uploadStatusId");
     // https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects	
    
    
    ajaxRequest ("POST", "/javakurs3-biliothek-jsf-mobile/fileUpload", formData, form, null, uploadStatusOutput,
    					 "Error uploading image", "/javakurs3-biliothek-jsf-mobile/pages/customers.jsf");
   
};

function isValidCustId(custId) {
	
	if (!custId){
		return false;
	}
		
	if (isNaN(custId)){
		return false;
	}
		
	if (Number(custId) <= 0){
		return false;
	}
		
	return true;
}


/**
 * sends an ajax request to the sendURL. Optionally (if not null) submits the form to the formActionURL
 * In case of error displays an errorMessage in the innerHTML of the errorMessageElement
 *
 * @param sendURL
 * @param formData
 * @param form
 * @param responseElement - if not null: changes the content of the responsElement with the server response
 * @param errorMessageElement
 * @param errorMessage
 * @param formActionURL
 * @returns
 */
function ajaxRequest (method, sendURL, formData, form, responseElement, errorMessageElement, errorMessage, formActionURL) {
	
    // https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects	
	var xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open(method, sendURL);
	
	// If specified, responseType must be empty string or "text"
	// @see https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/responseText
	xmlHttpRequest.responseType = 'text';
	
	xmlHttpRequest.onload = function(oEvent) {
		
		if (xmlHttpRequest.readyState === xmlHttpRequest.DONE) {
	        if (xmlHttpRequest.status === 200) {
	            console.log(xmlHttpRequest.response);
	            console.log(xmlHttpRequest.responseText);
	            
	            if (responseElement!=null) { // only in case the call is done for a customer's image
	            	responseElement.value = xmlHttpRequest.responseText;
	            	document.getElementById("libraryImageId").src = "/javakurs3-biliothek-jsf-mobile/resources/images/" + responseElement.value;
	            }
	            
	            if (xmlHttpRequest.responseText == "OK" && formActionURL != null ) {
	    	    	form.action = formActionURL;
	    	    	form.submit();
	    	    	return;
	            }
	        } 
	        
	        if (errorMessageElement!=null && errorMessage!=null){
	        	errorMessageElement.innerHTML = errorMessage;
	        };
	        
	    } // xmlHttpRequest.readyState === xmlHttpRequest.DONE
    };  // function (oEvent)
    
	xmlHttpRequest.send(formData);
}

/**
 * 
 * @param custId
 * @returns
 * @see
 * https://www.mkyong.com/jsf2/how-to-pass-new-hidden-value-to-backing-bean-in-jsf/
 * 
 */
function showCustomerImage () {
	
	 var custId = document.getElementById("customersform:selectedCustomerId");
	 
	 var form = document.getElementById('customersform');
	
	 if (form == null)
		 return;
	
     var formData = new FormData(form);
     formData.append ("custId", custId.value);
     
     var responseElement = document.getElementById("customersform:pathToImageId");
	 
	 ajaxRequest("POST", "/javakurs3-biliothek-jsf-mobile/imageRead", formData, form, responseElement, null, null, null);
}