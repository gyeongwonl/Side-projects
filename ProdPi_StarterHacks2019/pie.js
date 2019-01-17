
var myChart = document.getElementById('myChart');
if (myChart !== null) {
    var ctx = myChart.getContext('2d');
    var chart = new Chart(ctx, {
   
        type: 'doughnut',
    
        // The data for our dataset
        data: {
           // labels: ['facebook.com', 'two', 'three', 'four', 'five'],
           labels: website,
            datasets: [{
                label: "My First dataset",
                backgroundColor: [
                     'rgb(253, 226, 179)',
                     'rgb(239, 150, 134)',
                     'rgb(169, 96, 103)',
                     'rgb(180, 134, 137)',
                     'rgb(209, 191, 215)',
                     'rgb(115, 100, 143)',
                     'rgb(68, 43, 47)',
                     'rbg(151, 28, 46)',
                     'rgb(151, 28, 46)',
                     'rgb(242, 147, 140)'
                ],
                segmentStrokeWidth: 20,
                segmentStrokeColor: "rgba(255, 255, 255, 0.4)",
               
                
                //data: [1,2,3,5,6]
                data: time,
            }],
    
            //strokes
    
        },
    
        // Configuration options go here
        options: {
    
            //removing legend
            legend: {
                display: false
            },

            //doughnut
            cutoutPercentage: 85,

            //resizing
            //responsive: true,
            //maintainAspectRatio: false,

    
    
        }
    })
    counter++;
}