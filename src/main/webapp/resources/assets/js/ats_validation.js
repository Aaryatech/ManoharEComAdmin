//Sachin 09-07-2020

function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}

		function validateIfsc(temp) {
			var eml = /^[A-Za-z]{4}[a-zA-Z0-9]{7}$/;
			if (eml.test($.trim(temp)) == false) {
				return false;
			}
			return true;
		}

		function validateMicr(temp) {
			var eml = /^\d{9}$/;
			if (eml.test($.trim(temp)) == false) {
				return false;
			}
			return true;
		}
		
		function validateEmail(email) {
			var eml = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
			if (eml.test($.trim(email)) == false) {
				return false;
			}
			return true;
		}
		
		function validateMobile(mobile) {
			var mob = /^[1-9]{1}[0-9]{9}$/;
			if (mob.test($.trim(mobile)) == false) {
				return false;
			}
			return true;
		}
		
		function validatePAN(pan){
			var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
			if (regex1.test($.trim(pan)) == false) {
				return false;
			}else{
				return true
			}
		}
		
		
		
		/**
	     * Check empty object
	     */
	    function checkEmptyObject(obj) {
	        for (var key in obj) {
	            if (obj.hasOwnProperty(key))
	                return false;
	        }
	        return true;
	    }

	    /**
	     * Check if the string is empty or null
	     */
	    function checkNotNullAndNotEmpty(data) {
	        if (data !== null && data !== '') {
	            return true;
	        }
	        return false;
	    }

	    /**
	     * Check if the variable is undefined
	     */
	    function isUndefined(data) {
	        if (data === "undefined") {
	            return true;
	        }
	        return false;
	    }

	    /**
	     * "Safer" String.toLowerCase()
	     */
	    function lowerCase(str) {
	        return str.toLowerCase();
	    }

	    /**
	     * "Safer" String.toUpperCase()
	     */
	    function upperCase(str) {
	        return str.toUpperCase();
	    }

	    /**
	     * UPPERCASE first char of each word.
	     */
	    function properCase(str) {
	        return lowerCase(str).replace(/^\w|\s\w/g, upperCase);
	    }

	    /**
	     * UPPERCASE first char of each sentence and lowercase other chars.
	     */
	    function sentenceCase(str) {
	        // Replace first char of each sentence (new line or after '.\s+') to
	        // UPPERCASE
	        return lowerCase(str).replace(/(^\w)|\.\s+(\w)/gm, upperCase);
	    }


	    /**
	     * Searches for a given substring
	     */
	    function contains(str, substring, fromIndex) {
	        return str.indexOf(substring, fromIndex) !== -1;
	    }

	    /**
	     * Escapes a string for insertion into HTML.
	     */
	    function escapeHtml(str) {
	        str = str
	            .replace(/&/g, '&amp;')
	            .replace(/</g, '&lt;')
	            .replace(/>/g, '&gt;')
	            .replace(/'/g, '&#39;')
	            .replace(/"/g, '&quot;');

	        return str;
	    }

	    /**
	     * Unescapes HTML special chars
	     */
	    function unescapeHtml(str) {
	        str = str
	            .replace(/&amp;/g, '&')
	            .replace(/&lt;/g, '<')
	            .replace(/&gt;/g, '>')
	            .replace(/&#39;/g, "'")
	            .replace(/&quot;/g, '"');
	        return str;
	    }

	    /**
	     * Remove HTML tags from string.
	     */
	    function stripHtmlTags(str) {
	        return str.replace(/<[^>]*>/g, '');
	    }