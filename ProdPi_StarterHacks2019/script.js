var counter = 0;
var time =  [5, 10, 22, 4, 9, 3];
var website =  ["google.com", "facebook.com", "netflix.com", "youtube.com", "learn.uwaterloo.ca", "khanacademy.com"]; 

//functions HTML need
function totalTime(){
  return 53;
}

function totalTimeDisplay(){
  return 53 + 'min'
}

function mostTimeWeb(){
  return 'Neflix.com';
}

function mostTime(){
  return 22;
}

function mostTimePercent(){
  return Math.round ((100 * ( mostTime() / totalTime() ))) + '%'
}

function timeProductive() {
  return '75%';
}

//Timer function
function elementIndex(tab,array)
{
  for(var j=0; j<array.length; j++) 
  {
    if(array[j]==tab)
      return j;
  }
 return -1;
}

function timer() {
  
  var currentTab = "extension-Prod-Pi";
      // find it in array, add 1 second IMPLEMENT
      var i = elementIndex(currentTab, website);
      if (i == -1) {
        website.push(currentTab);
        time.push(0.01);
      }
      else {
        time[i] += 0.02;
        time[i] = Math.fround(time[i]*100)/100;
      }
if (counter !== 0) {
    chart.update();}
}
setInterval(timer, 1000);
