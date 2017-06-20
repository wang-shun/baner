var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F3417f160d30e38b8bbc5f16fd3eed5c7' type='text/javascript'%3E%3C/script%3E"));
var duoshuoQuery ={short_name:"myexception"};
(function(){
var ds = document.createElement('script');
ds.type = 'text/javascript';ds.async = true;
ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
ds.charset = 'UTF-8';
(document.getElementsByTagName('head')[0] 
 || document.getElementsByTagName('body')[0]).appendChild(ds);
})();
Cms ={};
Cms.viewCount = function (base, contentId, viewId, commentId, downloadId, upId, downId){
viewId = viewId || "views";
commentId = commentId || "comments";
downloadId = downloadId || "downloads";
upId = upId || "ups";
downId = downId || "downs";
$.getJSON(base + "/content_view.jspx",{contentId:contentId}, function (data){
if (data.length > 0){
$("#" + viewId).text(data[0]);
$("#" + commentId).text(data[1]);
$("#" + downloadId).text(data[2]);
$("#" + upId).text(data[3]);
$("#" + downId).text(data[4]);
}
});
};