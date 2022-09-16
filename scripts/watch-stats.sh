#!/bin/bash
# usage: watch.sh <your_command> <sleep_duration>

watch () {
while :; 
  do 
  clear
  date
  $1
  sleep $2
done
}


watch "docker ps" 5