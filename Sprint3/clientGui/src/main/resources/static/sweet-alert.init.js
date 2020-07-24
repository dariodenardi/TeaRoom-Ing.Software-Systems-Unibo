!function ($) {
    "use strict";

    var SweetAlert = function () {
    };

    //examples
    SweetAlert.prototype.init = function () {
    
		// CLIENTE 1
        //Success Message
        $('#request1').on('click', function () {
        
	        swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente vuole entrare!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "request1");
			});
			
				
		});
		
		//Success Message
        $('#ready1').on('click', function () {
        
        	swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente è pronto per ordinare!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "clientready1");
			});
			
		});
		
		//Success Message
        $('#order1').on('click', function () {
			
			swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente ha comunicato la sua scelta!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "order1");
			});
			
		});
		
		//Success Message
        $('#payment1').on('click', function () {
			
			swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente vuole pagare!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "payment1");
			});
			
		});
		
		// CLIENTE 2
		//Success Message
        $('#request2').on('click', function () {
			
			swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente vuole entrare!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "request2");
			});
			
		});
		
		//Success Message
        $('#ready2').on('click', function () {
            
			swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente è pronto per ordinare!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "clientready2");
			});
			
		});
		
		//Success Message
        $('#order2').on('click', function () {
            
			swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente ha comunicato la sua scelta!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "order2");
			});
			
		});
		
		//Success Message
        $('#payment2').on('click', function () {
            
			swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente vuole pagare!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "payment2");
			});
			
		});
		
		// CLIENTE 3
		//Success Message
        $('#request3').on('click', function () {
			
			swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente vuole entrare!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "request3");
			});
			
		});
		
		//Success Message
        $('#ready3').on('click', function () {
            
			swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente è pronto per ordinare!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "clientready3");
			});
			
		});
		
		//Success Message
        $('#order3').on('click', function () {
        
        	swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente ha comunicato la sua scelta!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "order3");
			});
			
		});
		
		//Success Message
        $('#payment3').on('click', function () {
            
			swal({ 
			  title: 'Richiesta inviata!',
			  text: "Il cliente vuole pagare!",
			  type: 'success',
			  },
			  function(){
			    sendRequestData( "payment3");
			});
			
		});

    },
        //init
        $.SweetAlert = new SweetAlert, $.SweetAlert.Constructor = SweetAlert
}(window.jQuery),

//initializing
    function ($) {
        "use strict";
        $.SweetAlert.init()
    }(window.jQuery);