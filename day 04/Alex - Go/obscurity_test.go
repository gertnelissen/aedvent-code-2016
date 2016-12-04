package main

import (
	"fmt"
	"testing"
	"reflect"
)

func TestSectorHash(t *testing.T) {
	tests := []struct {
		input  string
		sector int
		hash   string
	}{
		{"395[idjmx]", 395, "idjmx"},
	}

	for _, test := range tests {
		es, eh := sectorAndHash(test.input)
		if es != test.sector || eh != test.hash {
			t.Error("For", test.input,
				"expected", fmt.Sprintf("%v %s", test.sector, test.hash),
				"got", fmt.Sprintf("%v %s", es, eh))
		}
	}
}

func TestToRoom(t *testing.T) {
	line := "aaaaa-bbb-z-y-x-123[abxyz]"
	room := toRoom(line)
	expected := Room{123, "abxyz", "abxyz"}
	if !reflect.DeepEqual(room, expected) {
		t.Error("For", line,
			"expected", expected,
			"got", room)
	}

	if !room.IsCorrect() {
		t.Error("Room should be correct")
	}
}