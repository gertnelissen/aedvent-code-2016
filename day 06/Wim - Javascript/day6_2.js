"use strict";

const fs = require('fs');
const os = require('os');

// Reuse the zip magic of day 3, now in arrow notation 
function zip(arrays) {
    return arrays[0].map((_,i) => arrays.map(array => array[i]));
}

function getMinChar(input){
    const bucket = {};
    for(const char of input){      
        bucket[char] = bucket[char] ? bucket[char] + 1 : 1;  
    }

    const min = Object.keys(bucket).reduce((v, k) => bucket[k] <= v ? bucket[k] : v, Number.MAX_SAFE_INTEGER);
    return Object.keys(bucket).find(k => bucket[k] === min);
}

const input = fs.readFileSync("input.txt", "utf8" );
const rows = input
                .split(os.EOL)
                .map(r => r.split(''));
                
const result = zip(rows)
                .map(getMinChar)
                .join('');

console.log("Result: ", result);