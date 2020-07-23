!function ($) {
    "use strict";

    var SweetAlert = function () {
    };

    //examples
    SweetAlert.prototype.init = function () {

		// CLIENTE 1
        //Success Message
        $('#request1').on('click', function () {
            swal("Richiesta inviata!", "Il cliente vuole entrare", "success")
			sendRequestData( "request1")
		});
		
		//Success Message
        $('#ready1').on('click', function () {
            swal("Richiesta inviata!", "Il cliente è pronto a ordinare!", "success")
			sendRequestData( "clientready1")
		});
		
		//Success Message
        $('#order1').on('click', function () {
            swal("Richiesta inviata!", "Il cliente ha comunicato la sua scelta!", "success")
			sendRequestData( "order1")
		});
		
		//Success Message
        $('#payment1').on('click', function () {
            swal("Richiesta inviata!", "Il cliente vuole pagare!", "success")
			sendRequestData( "payment1")
		});
		
		// CLIENTE 2
		//Success Message
        $('#request2').on('click', function () {
            swal("Richiesta inviata!", "Il cliente vuole entrare", "success")
			sendRequestData( "request2")
		});
		
		//Success Message
        $('#ready2').on('click', function () {
            swal("Richiesta inviata!", "Il cliente è pronto a ordinare!", "success")
			sendRequestData( "clientready2")
		});
		
		//Success Message
        $('#order2').on('click', function () {
            swal("Richiesta inviata!", "Il cliente ha comunicato la sua scelta!", "success")
			sendRequestData( "order2")
		});
		
		//Success Message
        $('#payment2').on('click', function () {
            swal("Richiesta inviata!", "Il cliente vuole pagare!", "success")
			sendRequestData( "payment2")
		});
		
		// CLIENTE 3
		//Success Message
        $('#request3').on('click', function () {
            swal("Richiesta inviata!", "Il cliente vuole entrare", "success")
			sendRequestData( "request3")
		});
		
		//Success Message
        $('#ready3').on('click', function () {
            swal("Richiesta inviata!", "Il cliente è pronto a ordinare!", "success")
			sendRequestData( "clientready3")
		});
		
		//Success Message
        $('#order3').on('click', function () {
            swal("Richiesta inviata!", "Il cliente ha comunicato la sua scelta!", "success")
			sendRequestData( "order3")
		});
		
		//Success Message
        $('#payment3').on('click', function () {
            swal("Richiesta inviata!", "Il cliente vuole pagare!", "success")
			sendRequestData( "payment3")
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