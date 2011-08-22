<h1>GWT I18N Server-Side on GAE</h1>
It's possible to use the client GWT CONSTANTS/MESSAGES on the server side in Google App Engine.

You have to use the gwt-i18n-server project and modify it so that it works on GAE.

I have modified it for me and publish it here.
<img src="https://github.com/jjoe64/gwt-i18n-gae/raw/master/gwti18ngae.png" />

<h1>How to use</h1>
Copy the files in the <b>org.scb.gwt.web.server.i18n</b> folder in your project.
<hr/>
If you use it, you have to copy the CONSTANTS.properties (and MESSAGES, and other nls files) into the folder war/nls_server. You could also create a (svn) link.
<hr/>

Use it so in your Service or Servlet:
<pre>
// Internationalized "hello"
AppNLS nls = GWTI18N.create(AppNLS.class, "de"); // "de" is the language
return nls.hello() + " " + yourName;
</pre>

<h2>There's no fallback or inheritance support...</h2>

