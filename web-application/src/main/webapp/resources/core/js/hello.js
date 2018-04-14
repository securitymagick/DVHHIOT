/* ************************************************************
  Password Strength Meter and Other useful functions

************************************************************ */
function testPassword(passwd, documentPlace)
{
		var intScore   = 0
		var strVerdict = "weak"
		var strFont     = "red"
		
		
		if (passwd.length>7)// get 1 point for length greater than 8
		{
			intScore = passwd.length
		}	
		
		// LETTERS 
		if (passwd.match(/[a-z]/))                              // at least one lower case letter
		{
			intScore = (intScore+1)
		}
		
		if (passwd.match(/[A-Z]/))                              // at least one upper case letter
		{
			intScore = (intScore+1)
		}
		
		// NUMBERS
		if (passwd.match(/\d+/))                                 // at least one number
		{
			intScore = (intScore+1)
		}
		
		if (passwd.match(/(.*[0-9].*[0-9].*[0-9])/))             // at least three numbers
		{
			intScore = (intScore+3)
		}
		
		
		// SPECIAL CHAR
		if (passwd.match(/[^a-zA-Z0-9]/))            // at least one special character
		{
			intScore = (intScore+2)
		}
		
									 // [verified] at least two special characters
		if (passwd.match(/(.*[^a-zA-Z0-9].*[^a-zA-Z0-9])/))
		{
			intScore = (intScore+3)
		}
	    // check for common passwords
		if (passwd.match(/^(passw(o|0)rd1?|123456789?|trustno1|football|iloveyou|welcome|whatever|jordan23)$/)){
			intScore=0;
		}
		// check for common passwords
		if (passwd.match(/^(1qaz2wsx|12341234|corvette|computer|blahblah|matthew|mercedes)$/)){
			intScore=0;
		}		
		// check for common passwords
		if (passwd.match(/^(admin123|michelle|sunshine|zaq1zaq1|jennifer|maverick|starwars)$/)){
			intScore=0;
		}			
	
		if(intScore < 8)
		{
		   strVerdict = "Try a longer password or add a special character..."
		   strFont="red"
		}
		else if (intScore >= 8 && intScore < 14)
		{
		   strVerdict = "Pretty Good Choice if I do say so myself ..."
		   strFont = "cyan"
		}
		else if (intScore >= 14)
		{
		   strVerdict = "The best Password Ever!!!"
		   strFont="green"
		}
		document.getElementById(documentPlace).innerHTML = "<div class=\"row text-center\"><div class=\"col-md-5 col-md-offset-7\"><font color=\"" + strFont + "\">" + strVerdict + "</font></div></div>"
}

function testPasswordMatch(passwd, confirmPasswd, documentPlace) {
	var strVerdict = "Passwords Do Not Match!"
	var strFont     = "red"
	
	if (passwd === confirmPasswd) {
		strVerdict = "Passwords Match ... Great Job!"
		strFont     = "green"
	}
		
	document.getElementById(documentPlace).innerHTML = "<div class=\"row text-center\"><div class=\"col-md-5 col-md-offset-7\"><font color=\"" + strFont + "\">" + strVerdict + "</font></div></div>"	
}

function testUsername(user, documentPlace, submitButton)
{
		var intScore   = 0
		var strVerdict = "choose again"
		var strFont     = "red"
		
		
		if (user.length>2 && user.length<33)// get 1 point for length greater than 2
		{
			intScore = user.length
		}	
		
		// LETTERS 
		if (user.match(/[a-z]/))                              // at least one lower case letter
		{
			intScore = (intScore+1)
		}
		
		if (user.match(/[A-Z]/))                              // at least one upper case letter
		{
			intScore = (intScore+1)
		}
		
		// NUMBERS
		if (user.match(/\d+/))                                 // at least one number
		{
			intScore = (intScore+1)
		}
		
		if (user.match(/(.*[0-9].*[0-9].*[0-9])/))             // at least three numbers
		{
			intScore = (intScore+3)
		}
		
		
		// SPECIAL CHAR
		if (user.match(/[^a-zA-Z0-9]/))            // at least one special character
		{
			intScore = 0
		}
		
	    // check for common passwords
		if (user.match(/^(Lizzie|Duck|John|Bob|admin|root|superuser)$/)){
			intScore=0;
		}
		
		if(intScore < 4)
		{
		   strVerdict = "Choose something different!  No special character please."
		   strFont="red"
		   submitButton.setAttribute('disabled', true); 
		}
		else if (intScore >= 4)
		{
		   strVerdict = "Good Choice!"
		   strFont="green"
		   submitButton.removeAttribute('disabled');	  	   
		}
		document.getElementById(documentPlace).innerHTML = "<div class=\"row text-center\"><div class=\"col-md-5 col-md-offset-7\"><font color=\"" + strFont + "\">" + strVerdict + "</font></div></div>"
}

function testText(text, documentPlace, submitButton)
{
		var intScore   = 1;
		var strVerdict = "";
		var strFont     = "green";
		
		

		
	    // check for common ways to start java
		if (text.match(/(<script|<SCRIPT|<img src="javascript:|<img src=javascript:)/)){
			intScore=0;
		} 
		
		if(intScore < 1)
		{
		   strVerdict  = "We love you making your posts and comments look good with tags, but for security reason can't allow javascript.";
		   strFont="red";
		   submitButton.setAttribute('disabled', true); 	   
		}
		else {
			submitButton.removeAttribute('disabled');
			if (text.length >23)
			{
			   strVerdict = "Awesome Thoughts!"
			   strFont="green";
			}
		} 
		
		document.getElementById(documentPlace).innerHTML = "<div class=\"row text-center\"><div class=\"col-md-5 col-md-offset-7\"><font color=\"" + strFont + "\">" + strVerdict + "</font></div></div>"
}

function createCookie(name,value,days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + value + expires + "; path=/habit-helper-1.0";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

function inspiringNotification(imgPlace, txtPlace) {
	inspiringImage(imgPlace);
	var say ="<ul><li>You are doing just great!</li>";
	var y = Math.floor((Math.random() * 3) + 1);
    if (y == 1) {
    	say ="<ul><li>Look at what you have accomplished!</li>";
    } else if (y==2) {
    	say="<ul><li>Keep doing what your doing for success!</li>";
    }
    	
    var n;
    var ncookie= readCookie('notifications');
    if (ncookie == null) {
    	n = "none";
    }
    else {
    	var nc = ncookie.replace(/^"(.*)"$/, '$1');
    
    	n = atob(nc);
	}
    var a = n.split(","),
    i;

    for (i = 0; i < a.length; i++) {
    	if (a[i]!="none") {
    		say +="<li>" + inspiringWords(a[i]) + "</li>"; 
    	}    	   	
    }
   
	//if (post) {
	//	say +="<li>" + inspiringWords("post") + "</li>"; 
	//}
	//if (comment) {
	//	say +="<li>" + inspiringWords("comment") + "</li>"; 
	//}	
	var score = parseInt(readCookie("score"));
	say +="<li>You've resisted your habit " + score + " times today.</li>";
	say += "</ul>";
	document.getElementById(txtPlace).innerHTML = say;
	createCookie('notifications', btoa('none'), 1);
}


function inspiringImage(documentPlace) {
	var imgClass = "center-block";
    var x = Math.floor((Math.random() * 4) + 1);
    var y = Math.floor((Math.random() * 3) + 1);
    if (y == 1) {
    	imgClass = "center-block img-rounded";
    } else if (y == 2) {
    	imgClass = "center-block img-circle";
    }
    document.getElementById(documentPlace).innerHTML = "<img class=\"" + imgClass + "\" src=\"/habit-helper-1.0/resources/core/images/basic-images/" + x + ".jpg\"/>";
}

function inspiringWords(type) {

	var say = "Awesome " + type + "!";
	var y = Math.floor((Math.random() * 10) + 1);
	
	if (y == 1) {
		say = "Very thoughtful " + type + ".  Good job!";
    } else if (y == 2) {
    	say = "Super duper good " + type + "!  I wish I had said that.";
    }else if (y == 3) {
    	say = "Excellent " + type + "!  If I do say so myself.";
    }else if (y == 4) {
    	say = "Your really helping others with the great " + type + "s.";
    }else if (y == 5) {
    	say = "Fantastic " + type + "!  Keep it up.";
    }else if (y == 6) {
    	say = "Superb " + type + "!  I never thought of that before.";
    }else if (y == 7) {
    	say = "Inspiring " + type + ".  Thanks for all the effort.";
    }else if (y == 8) {
    	say = "Can I just say you are amazing and that " + type + " is amazing.";
    }else if (y == 9) {
    	say = "Magnificent " + type + "!  Thanks for helping the habit helper community.";
    }
	
	return say;
}


 
