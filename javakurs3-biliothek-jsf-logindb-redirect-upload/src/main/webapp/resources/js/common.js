 var fileList = []; // an array of objects representing all the files in the HTML table and a comment to each file
 var selectedRows = []; // the indexes of the selected rows from the HTML files table
      
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
			var pview = document.getElementById('preview');
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
    
     // https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects	
	var request = new XMLHttpRequest();
	request.open("POST", "/bibliothek/fileUpload");
	request.send(formData);
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