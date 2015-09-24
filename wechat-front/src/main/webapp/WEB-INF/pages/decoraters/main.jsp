<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        
        <link rel="stylesheet" href="${res_static}/wx/css/p/standard/public.css">
        <link rel="stylesheet" href="${res_static}/wx/css/p/standard/standard.css">
        
        <sm:insert property="css" />
    </head>
    <body>
        <!--html beging-->
        <div class="page">

        	<!--insert header -->
            <sm:insert property="header">
            	<header class="header">
            		<%
            			String _referUrl = request.getHeader("referer");
						if (_referUrl != null) {
					%>
						<a href="javascript:history.go(-1);" class="back comm_20_down"></a>
					<%}%>
			    	${title}
			    </header>
            </sm:insert>
        
            <!--insert content -->
            <sm:insert property="content" />
        </div>
        
        <sm:insert property="footer" />
        <!--html end-->
    </body>
    
    <script type="text/javascript" src="${res_static}/wx/js/p/standard/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="${res_static}/wx/js/p/standard/jquery.qrcode-0.11.0.min.js"></script>
    
    <script type="text/javascript">
	    $.extend({
		    wxAjax : function(obj){
		    	$.getJSON(obj.url, obj.param, function(data){
		    		if(data.code == 801){
		    			window.location.href="/toRegister?sourceUrl="+window.location.href;
		    		}else{
		    			obj.fn(data);
		    		}
		    	});
		    }
	    });
    </script>
    
    <!--insert bussiness js -->
    <sm:insert property="js" />
    
</html>