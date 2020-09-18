<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>供应商管理页面 >> 供应商添加页面</span>
        </div>
        <div class="providerAdd">
           <fm:form modelAttribute="provider" id="providerForm" name="providerForm" method="post" action="${pageContext.request.contextPath }/provider/provideraddsave.html">
			<input type="hidden" name="method" value="add">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div class="">
                    <label for="proCode">供应商编码：</label>
                    <fm:input type="text" name="proCode" id="proCode" value="" path="proCode"/>
					<!-- 放置提示信息 -->
					<font color="red"></font><fm:errors path="proCode"/>
                </div>
                <div>
                    <label for="proName">供应商名称：</label>
                   <fm:input type="text" name="proName" id="proName" value="" path="proName"/>
					<font color="red"></font>
                </div>
                <div>
                    <label for="proContact">联系人：</label>
                    <fm:input type="text" name="proContact" id="proContact" value="" path="proContact"/>
					<font color="red"></font>

                </div>
                <div>
                    <label for="proPhone">联系电话：</label>
                    <fm:input type="text" name="proPhone" id="proPhone" value="" path="proPhone"/>
					<font color="red"></font>
                </div>
                <div>
                    <label for="proAddress">联系地址：</label>
                    <fm:input type="text" name="proAddress" id="proAddress" value="" path="proAddress"/>
                </div>
                <div>
                    <label for="proFax">传真：</label>
                    <fm:input type="text" name="proFax" id="proFax" value="" path="proFax"/>
                </div>
                <div>
                    <label for="proDesc">描述：</label>
                    <fm:input type="text" name="proDesc" id="proDesc" value="" path="proDesc"/><fm:errors path="proDesc"/>
                </div>
                <div class="providerAddBtn">
                    <input type="button" name="add" id="add" value="保存">
					<input type="button" id="back" name="back" value="返回" >
                </div>
            </fm:form>
     </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/provideradd.js"></script>
