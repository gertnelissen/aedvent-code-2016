package main

import (
	"testing"
)

func TestHash(t *testing.T) {
	result := hash("abc", 3231929)
	if result != "00000155f8105dff7f56ee10fa9b9abd" {
		t.Error("Wrong hash")
	}
	c := make(chan string)
	go findGoodHash("abc", 3231929, c)
	letter := <-c
	if letter != "1" {
		t.Error("Did not receive correct letter from hash")
	}
}
