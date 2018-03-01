
PrimeFaces.locales["es"] = {
    closeText: "Cerrar",
    prevText: "Anterior",
    nextText: "Siguiente",
    monthNames: ["Enero","Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
    monthNamesShort: ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"],
    dayNames: ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"],
    dayNamesShort: ["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"],
    dayNamesMin: ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"],
    weekHeader: "Semana",
    firstDay: 0,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: "",
    timeOnlyTitle: "Solo hora",
    timeText: "Tiempo",
    hourText: "Hora",
    minuteText: "Minuto",
    secondText: "Segundo",
    currentText: "Fecha actual",
    ampm: false,
    month: "Mes",
    week: "Semana",
    day: "Día",
    allDayText : "Todo el día"
};


function dormir(tiempo){
    var now = new Date().getTime();
    while(new Date().getTime() < now + tiempo){ /* do nothing */ } 
}

function changeTo(panelaMostrar,panelaEsconder){
    if(panelaEsconder!=''){
        document.getElementById(panelaEsconder).style.display="none";
    }
    dormir(100);
    if(panelaMostrar!=''){
        document.getElementById(panelaMostrar).style.display="block";
    }
}

function start() {
    PF('statusDialog').show();
}
 
function stop() {
    PF('statusDialog').hide();
}

