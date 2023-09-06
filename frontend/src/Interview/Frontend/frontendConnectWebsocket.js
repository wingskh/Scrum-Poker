const socket = new SockJS('/story-point-update');
const stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/topic/story-point-update', function (update) {
        console.log('Received story point update: ', JSON.parse(update.body));
        // Update the frontend with the latest story point
        // ...
    });
});