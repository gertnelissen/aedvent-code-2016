package main

import (
	"crypto/md5"
	"encoding/hex"
	"fmt"
	"strings"
)

func main() {
	c := make(chan string, 8)
	quit := make(chan bool)
	go sendCommands("ffykfhsq", c, quit)
	p := [8]string{}
	p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7] = <-c, <-c, <-c, <-c, <-c, <-c, <-c, <-c
	quit <- true
	fmt.Printf("The password is %v", strings.Join(p[:], ""))
}

func sendCommands(prefix string, c chan string, quit chan bool) {
	i := 0
	for {
		select {
		case <-quit:
			return
		default:
			go findGoodHash(prefix, i, c, quit)
			i++
		}
	}
}

func findGoodHash(prefix string, i int, c chan string, quit chan bool) {
	select {
	case <-quit:
		return
	default:
		hash := hash(prefix, i)
		if strings.HasPrefix(hash, "00000") {
			c <- string(hash[5])
		}
	}
}

func hash(prefix string, i int) string {
	md5HashInBytes := md5.Sum([]byte(fmt.Sprintf("%v%v", prefix, i)))
	return hex.EncodeToString(md5HashInBytes[:])
}
