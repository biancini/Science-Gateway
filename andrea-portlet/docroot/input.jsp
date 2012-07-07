<%
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />
<%//
  // GATE 6.0.0 Submission Form
  //
  // The form has 3 input textareas respectively for:
  //    * Macro file
  //    * Material DB
  //    * ROOT Analysis C file
  // Beside each text area a upolad button takes as input the 
  // file name related to one of the above fields.
  // The forth submission buttons is related to the file 'phsp.root' file
  // A default phsp.root file will be used if no files will be uploaded by the user.
  // The ohter three buttons of the form are used for:
  //    o Demo:          Used to fill with demo values the text areas
  //    o SUBMIT:        Used to execute the job on the eInfrastructure
  //    o Reset values:  Used to reset input fields
  //  
%>

<%
// Below the descriptive area of the GATE web form 
%>
<table>
<tr>
<td valign="top">
<img align="left" style="padding:10px 10px;" src="<%=renderRequest.getContextPath()%>/images/AppLogo.png" />
</td>
<td>
Please fill the following form and then press <b>'SUBMIT'</b> button to launch this application.<br>
Requested inputs are:
<ul>
	<li>An input file (its text or a file to upload)</li>
        <li>Any other input ...</li>
</ul>
Pressing the <b>'Demo'</b> Button input fields will be filled with Demo values.<br>
Pressing the <b>'Reset'</b> Button all input fields will be initialized.<br>
Pressing the <b>'About'</b> Button information about the application will be shown
</td>
<tr>
</table align="center">
<%
// Below the application submission web form 
//
// The <form> tag contains a portlet parameter value called 'PortletStatus' the value of this item
// will be read by the processAction portlet method which then assigns a proper view mode before
// the call to the doView method.
// PortletStatus values can range accordingly to values defined into Enum type: Actions
// The processAction method will assign a view mode accordingly to the values defined into
// the Enum type: Views. This value will be assigned calling the function: setRenderParameter
//
%>
<center>
<form enctype="multipart/form-data" action="<portlet:actionURL portletMode="view"><portlet:param name="PortletStatus" value="ACTION_SUBMIT"/></portlet:actionURL>" method="post">
<dl>	
	<!-- This block contains: label, file input and textarea for GATE Macro file -->
	<dd>		
 		<p><b>Application' input file</b> <input type="file" name="file_inputFile" id="upload_inputFileId" accept="*.*" onchange="uploadInputFile()"/></p>
		<textarea id="inputFileId" rows="20" cols="100%" name="inputFile">Insert here your text file, or upload a file</textarea>
	</dd>
	<!-- This block contains the experiment name -->
	<dd>
		<p>Insert below your <b>job identifyer</b></p>
		<textarea id="jobIdentifierId" rows="1" cols="60%" name="JobIdentifier">multi-infrastructure job description</textarea>
	</dd>	
	<!-- This block contains form buttons: Demo, SUBMIT and Reset values -->
  	<dd>
  		<td><input type="button" value="Demo" onClick="addDemo()"></td>
  		<td><input type="button" value="Submit" onClick="preSubmit()"></td> 
  		<td><input type="reset" value="Reset values" onClick="resetForm()"></td>
  	</dd>
</dl>
</form>
   <tr>
        <form action="<portlet:actionURL portletMode="HELP"> /></portlet:actionURL>" method="post">
        <td><input type="submit" value="About"></td>
        </form>        
   </tr>
</table>
</center>

<%
// Below the javascript functions used by the GATE web form 
%>
<script language="javascript">
//
// preSubmit
//
function preSubmit() {  
    var inputFileName=document.getElementById('upload_inputFileId');
    var inputFileText=document.getElementById('inputFileId');
    var jobIdentifier=document.getElementById('jobIdentifierId');
    var state_inputFileName=false;
    var state_inputFileText=false;
    var state_jobIdentifier=false;
    
    if(inputFileName.value=="") state_inputFileName=true;
    if(inputFileText.value=="" || inputFileText.value=="Insert here your text file, or upload a file") state_inputFileText=true;
    if(jobIdentifier.value=="") state_jobIdentifier=true;    
       
    var missingFields="";
    if(state_inputFileName && state_inputFileText) missingFields+="  Input file or Text message\n";
    if(state_jobIdentifier) missingFields+="  Job identifier\n";
    if(missingFields == "") {
      document.forms[0].submit();
    }
    else {
      alert("You cannot send an inconsisten job submission!\nMissing fields:\n"+missingFields);
        
    }
}
//
//  uploadMacroFile
//
// This function is responsible to disable the related textarea and 
// inform the user that the selected input file will be used
function uploadInputFile() {
	var inputFileName=document.getElementById('upload_inputFileId');
	var inputFileText=document.getElementById('inputFileId');
	if(inputFileName.value!='') {
		inputFileText.disabled=true;
		inputFileText.value="Using file: '"+inputFileName.value+"'";
	}
}

//
//  resetForm
//
// This function is responsible to enable all textareas
// when the user press the 'reset' form button
function resetForm() {
	var currentTime = new Date();
	var inputFileName=document.getElementById('upload_inputFileId');
	var inputFileText=document.getElementById('inputFileId');
	var jobIdentifier=document.getElementById('jobIdentifierId');
        
        // Enable the textareas
	inputFileText.disabled=false;
	inputFileName.disabled=false;        			
            
	// Reset the job identifier
        jobIdentifier.value="Job execution of: "+currentTime.getDate()+"/"+currentTime.getMonth()+"/"+currentTime.getFullYear()+" - "+currentTime.getHours()+":"+currentTime.getMinutes()+":"+currentTime.getSeconds();
}

//
//  addDemo
//
// This function is responsible to enable all textareas
// when the user press the 'reset' form button
function addDemo() {
	var currentTime = new Date();
	var inputFileName=document.getElementById('upload_inputFileId');
	var inputFileText=document.getElementById('inputFileId');
	var jobIdentifier=document.getElementById('jobIdentifierId');
	
	// Disable all input files
        inputFileText.disabled=false;
	inputFileName.disabled=true;
	
	// Secify that the simulation is a demo
	jobIdentifier.value="multi-infrastructure demo job description";
	
        // Add the demo value for the GATE macro file
	inputFileText.value="This is the demo file content ...";
}
</script>
