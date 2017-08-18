var inputPrompt = function(successCallback, errorCallback, title, placeholder, okbuttontext, cancelbuttontext){
    
    cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'InputPrompt', // mapped to our native Java class called "CalendarPlugin"
            'input-prompt', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "title": title,
                "placeholder": placeholder,
                "okbuttontext": okbuttontext,
                "cancelbuttontext": cancelbuttontext
            }]
        ); 
}
module.exports = inputPrompt;
