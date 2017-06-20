var browser = {
  versions: function () {
    var u = navigator.userAgent,
    app = navigator.appVersion;
    return {
      trident: u.indexOf('Trident') > - 1,
      presto: u.indexOf('Presto') > - 1,
      webKit: u.indexOf('AppleWebKit') > - 1,
      gecko: u.indexOf('Gecko') > - 1 && u.indexOf('KHTML') == - 1,
      mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/),
      ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
      android: u.indexOf('Android') > - 1 || u.indexOf('Linux') > - 1,
      iPhone: u.indexOf('iPhone') > - 1 || u.indexOf('Mac') > - 1,
      iPad: u.indexOf('iPad') > - 1,
      webApp: u.indexOf('Safari') == - 1,
      QQbrw: u.indexOf('MQQBrowser') > - 1,
      ucLowEnd: u.indexOf('UCWEB7.') > - 1,
      ucSpecial: u.indexOf('rv:1.2.3.4') > - 1,
      ucweb: function () {
        try {
          return parseFloat(u.match(/ucweb\d+\.\d+/gi).toString().match(/\d+\.\d+/).toString()) >= 8.2
        } catch (e) {
          if (u.indexOf('UC') > - 1) {
            return true
          } else {
            return false
          }
        }
      }(),
      Symbian: u.indexOf('Symbian') > - 1,
      ucSB: u.indexOf('Firefox/1.') > - 1
    };
  }()
};
var _gaq = _gaq || [];
(function (win, browser, undefined) {
  var rf = document.referrer;
  if (rf === '' || rf.toLocaleLowerCase().split('/') [2].indexOf('m.myexception.cn') === - 1) {
    if (win.screen === undefined || win.screen.width < 810) {
      if (browser.versions.iPad == true) {
        return;
      }
      if (browser.versions.webKit == true || browser.versions.mobile == true || browser.versions.ios == true || browser.versions.iPhone == true || browser.versions.ucweb == true || browser.versions.ucSpecial == true) {
          win.location.href = "http://m.myexception.cn"+win.location.pathname;
        return;
      }
      if (browser.versions.Symbian) {
    	  win.location.href = "http://m.myexception.cn"+win.location.pathname;
      }
    }
  }
}) (this, browser);

function h_a(){
}
function m_a(){
}
function c_a_0(){
document.write("<div class=\"bdsharebuttonbox\"><a href=\"#\" class=\"bds_more\" data-cmd=\"more\"></a><a title=\"分享到QQ空间\" href=\"#\" class=\"bds_qzone\" data-cmd=\"qzone\"></a><a title=\"分享到新浪微博\" href=\"#\" class=\"bds_tsina\" data-cmd=\"tsina\"></a><a title=\"分享到腾讯微博\" href=\"#\" class=\"bds_tqq\" data-cmd=\"tqq\"></a><a title=\"分享到人人网\" href=\"#\" class=\"bds_renren\" data-cmd=\"renren\"></a><a title=\"分享到微信\" href=\"#\" class=\"bds_weixin\" data-cmd=\"weixin\"></a></div>");
window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
}
function c_a_1(){
}
function c_a_2(){
document.write('<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"><\/script>');
document.write('<ins class="adsbygoogle" style="display:inline-block;width:300px;height:250px" data-ad-client="ca-pub-1729556811514431" data-ad-slot="4293937028"></ins>');
document.write("<script>(adsbygoogle = window.adsbygoogle || []).push({});<\/script>")
}
function c_a_2_a(){
document.write("<script type=\"text/javascript\"> var cpro_id = 'u1107459';</script>");
document.write("<script src=\"http://cpro.baidustatic.com/cpro/ui/c.js\" type=\"text/javascript\"></script>");
}
function c_a_3(){
}
function c_a_4(){
document.write("<script type=\"text/javascript\"> var cpro_id = 'u1072259';</script>");
document.write("<script src=\"http://cpro.baidustatic.com/cpro/ui/c.js\" type=\"text/javascript\"></script>");
}
function c_a_4_a(){
document.write('<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"><\/script>');
document.write('<ins class="adsbygoogle" style="display:inline-block;width:300px;height:250px" data-ad-client="ca-pub-1729556811514431" data-ad-slot="9353215580"></ins>');
document.write("<script>(adsbygoogle = window.adsbygoogle || []).push({});<\/script>")
}
function c_a_4_1(){
}
function c_a_5(){
}
function c_a_5_1(){
document.write('<div style="font-size:12px; color:#7b7d7f;width:601px;margin:10px 0 10px 15px;">发布此文章仅为传递网友分享，不代表本站观点，若侵权请联系我们删除，本站将不对此承担任何责任。</div>');
}
function c_a_6(){
}
function c_a_7(){
}
function c_a_8(){
}
function c_a_9(){
}
function c_a_10(){
}
function c_a_11(){
}
function c_a_12(){
}
function c_r_1_a(){
}
function c_r_2_a(){
document.write('<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"><\/script>');
document.write('<ins class="adsbygoogle" style="display:inline-block;width:300px;height:250px" data-ad-client="ca-pub-1729556811514431" data-ad-slot="2153680843"></ins>');
document.write("<script>(adsbygoogle = window.adsbygoogle || []).push({});<\/script>")
}
function c_r_3_a(){
}
function c_r_4_a(){
}
function c_r_5_a(){
}
function c_r_6_a(){
}
function c_r_7_a(){
}
function c_r_8_a(){
}
function c_r_9_a(){
}
function c_r_10_a(){
}
document.write("<script type=\"text/javascript\"> var cpro_id = 'u1107442';</script>");
document.write("<script src=\"http://cpro.baidustatic.com/cpro/ui/f.js\" type=\"text/javascript\"></script>");
function col_m(b){
for (var a = 1; a < 4; a++){
document.getElementById("e" + a).style.display = "none";
document.getElementById("s" + a).className = "";
}
document.getElementById("e" + b).style.display = "block";
document.getElementById("s" + b).className = "on";
}

document.write("<script type=\"text/javascript\" src=\"http://cbjs.baidu.com/js/m.js\"></script><script type=\"text/javascript\">BAIDU_CLB_fillSlot(\"977816\");</script>");