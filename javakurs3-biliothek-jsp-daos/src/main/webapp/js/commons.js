function hideUndideFields() {

	var kundeTyp = document.getElementById("kundeTypId").value;

	var firstTwoRowsVidible = (kundeTyp == "1" || kundeTyp == "2") ? "visible"
			: "hidden";
	document.getElementById("vorNameRowId").style.visibility = firstTwoRowsVidible;
	document.getElementById("adresseId").style.visibility = firstTwoRowsVidible;

	var restOfFieldsVidible = kundeTyp == "1" ? "visible" : "hidden";
	document.getElementById("nachNameRowId").style.visibility = restOfFieldsVidible;
	document.getElementById("gebDatumId").style.visibility = restOfFieldsVidible;

	var steuerNrVisible = kundeTyp == "2" ? "visible" : "hidden";
	document.getElementById("steuerNummerId").style.visibility = steuerNrVisible;

	//document.getElementById("buttonsId").style.visibility = firstTwoRowsVidible;
}