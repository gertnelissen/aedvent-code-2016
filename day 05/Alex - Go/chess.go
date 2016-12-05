package main

import (
	"crypto/md5"
	"encoding/hex"
	"flag"
	"fmt"
	"strings"
)

func main() {
	input := flag.String("input", "ffykfhsq", "input string")
	part := flag.Int("part", 1, "part 1 or 2?")
	flag.Parse()
	if *part == 1 {
		part1(*input)
	} else {
		part2(*input)
	}
}

func part1(input string) {
	c := make(chan string, 8)
	quit := make(chan bool)
	go dispatch(input, c, quit)
	p := [8]string{}
	p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7] = <-c, <-c, <-c, <-c, <-c, <-c, <-c, <-c
	fmt.Printf("The password is %v", strings.Join(p[:], ""))
	close(quit)
}

func dispatch(prefix string, c chan string, quit <-chan bool) {
	i := 0
	for {
		select {
		case <-quit:
			return
		default:
			go findGoodHash(prefix, i, c)
			i++
		}
	}
}

func findGoodHash(prefix string, i int, c chan<- string) {
	hash := hash(prefix, i)
	if strings.HasPrefix(hash, "00000") {
		c <- string(hash[5])
	}
}

func hash(prefix string, i int) string {
	md5HashInBytes := md5.Sum([]byte(fmt.Sprintf("%v%v", prefix, i)))
	return hex.EncodeToString(md5HashInBytes[:])
}
