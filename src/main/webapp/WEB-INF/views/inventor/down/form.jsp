<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${command == 'create'}">
			<acme:input-textbox code="inventor.down.form.label.code" path="code"/>
			<acme:input-select code="inventor.down.form.label.item" path="items">
            	<jstl:forEach items="${items}" var="items">
                	<acme:input-option code="${items}" value="${items}" selected="${items}"/>
            	</jstl:forEach>
        	</acme:input-select>
		</jstl:when>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:input-textbox code="inventor.down.form.label.code" path="code" readonly="true"/>
			<acme:input-textbox code="inventor.down.form.label.item" path="item" readonly="true"/>
			<acme:input-moment code="inventor.down.form.label.creationMoment" path="creationMoment" readonly="true"/>
		</jstl:when>
	</jstl:choose>
    <acme:input-textbox code="inventor.down.form.label.subject" path="subject"/>
    <acme:input-textarea code="inventor.down.form.label.explanation" path="explanation"/>
    <acme:input-moment code="inventor.down.form.label.startPeriod" path="startPeriod"/>
    <acme:input-moment code="inventor.down.form.label.endPeriod" path="endPeriod"/>
    <acme:input-money code="inventor.down.form.label.quantity" path="quantity"/>
    <acme:input-url code="inventor.down.form.label.additionalInfo" path="additionalInfo"/>
    
    	<jstl:choose>
			<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
				<acme:submit code="inventor.down.form.button.update" action="/inventor/down/update"/>
				<acme:submit code="inventor.down.form.button.delete" action="/inventor/down/delete"/>
			</jstl:when>	
		
			<jstl:when test="${command == 'create'}">
				<acme:submit code="inventor.down.form.button.create" action="/inventor/down/create"/>
			</jstl:when>
		</jstl:choose>
    
</acme:form>