/*global webAggregator _config jQuery*/

var searchButton = window.searchButton || {};
var outputFrame = window.outputFrame || {};

(function aggregatorScopeWrapper($) {
    
    function getTopics(query, user) {
        $.ajax({
            method: 'POST',
            url: 'https://9e5oai8ycf.execute-api.us-east-1.amazonaws.com/dev/topics',
            data: JSON.stringify({
                PickupLocation: {
                    Query: query,
                    Username: user
                }
            }),
            contentType: 'application/json',
            success: completeRequest,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error requesting ride: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert('An error occured when requesting your unicorn:\n' + jqXHR.responseText);
            }
        });
    }

    function completeRequest(result) {
        var matches;
        console.log('Response received from API: ', result);
        window.alert(result);
    }

    // Register click handler for #request button
    $(function onDocReady() {
        
        console.log("check 0");
        $('#searchButton').click(handleSearchClick);
        console.log("check 1");
        
        if (!_config.api.invokeUrl) {
            $('#noApiMessage').show();
        }
    });

    function handleSearchClick(event) {
        console.log("Getting query")
        var query = $('#query').getText();
        console.log("Query = ", query)
        console.log("Getting username")
        var user = $('#username').getText();
        console.log("Username = ", query)
        console.log("Calling get topics")
        getTopics(query, user);
        console.log("Finished calling get topics")
    }
    
    function displayUpdate(text) {
        $('#updates').append($('<li>' + text + '</li>'));
    }
}(jQuery));