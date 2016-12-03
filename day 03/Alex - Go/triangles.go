package main

import (
	"os"
	"log"
	"bufio"
	"fmt"
	"strings"
	"strconv"
)

func main() {
	triangles := readInput()
	count := 0
	for _, t := range triangles {
		if t.possible() {
			count++
		}
	}
	fmt.Printf("There are %v possible triangles", count)
}

type triangle struct {
	a,b,c int
}

func (t triangle) possible() bool {
	return t.a + t.b > t.c && t.a + t.c > t.b && t.b + t.c > t.a
}

func readInput() []triangle {
	file, err := os.Open("input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()
	result := make([]triangle, 0)

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		t := convertToTriangle(scanner.Text())
		result = append(result, t)
	}
	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
	return result
}

func convertToTriangle(input string) triangle {
	sides := strings.Fields(input)
	return triangle{toInt(sides[0]), toInt(sides[1]), toInt(sides[2])}
}

func toInt (s string) int {
	result, err := strconv.Atoi(s)
	if err != nil {
		panic(err)
	}
	return result
}