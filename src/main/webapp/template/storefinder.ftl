<!DOCTYPE html>
<html>
	<head>
    	<meta charset="UTF-8">
    	<title>Store-Finder</title>
    	<link rel="stylesheet" type="text/css" href="css/storefinderstyle.css">
	</head>
	<body>
		<#assign complex_store_map = {"branches": (branches)}>
		
		<div data-qa="store_finder" class="store_finder">
    		<h4 class="storeFinderHeadline">${headline}</h4>
    		<div class="stores">
    			<p class="stores_info">${info}</p>
    			<div class="stores_list">
    				<select name="js-stores" onChange="window.location.href='https://www.mytoys.de'+this.value">
    					<option value="#" selected="selected">Filiale w&aumlhlen</option>
    					<#list complex_store_map.branches as store>
    						<option value=${store.url}>${store.name}</option>
    					</#list>
    				</select>
    			</div>
    			<#if location != "0">
    				<button onclick="showLocation()">Welche Filiale ist am n&aumlchsten?</button>
    				<p id="locationText"></p>
    				<script>
    					function showLocation() {
    						document.getElementById("locationText").innerHTML = "${location}";
    					}
    				</script>
    			<#else>
    				<p></p>
    			</#if>
    		</div>
    	</div>
	</body>
</html>