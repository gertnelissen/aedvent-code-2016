package main

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
	"strconv"
	"time"
)

var handover, inputbin *regexp.Regexp
var answerFound func(int, int) bool
var answer chan int
var bins map[int]int

func main() {
	part1()
	fmt.Printf("Solution to Part 2: %v\n", bins[0]*bins[1]*bins[2])
}

func part1() {
	defer track(time.Now(), "part 1")
	answerFound = endCondition(61, 17)
	answer = make(chan int)
	commands := make(chan command, 50)
	go readInput(commands)
	go dispatcher(commands)
	droid := <-answer
	fmt.Printf("The droid you're looking for is %v \n", droid)
}

type command struct {
	value
	valueCmd string
	source   int
}

type value struct {
	destType          string
	destNumber, value int
}

type droid struct {
	input    chan int
	commands chan command
	id       int
	output   chan command
}

//A balance bot can receive up to two values and then pass them on
func bot(droid *droid) {
	val1, val2 := <-droid.input, <-droid.input
	if answerFound(val1, val2) {
		answer <- droid.id
	}
	if val2 < val1 {
		val1, val2 = val2, val1
	}
	cmd1, cmd2 := <-droid.commands, <-droid.commands
	if cmd1.valueCmd == "low" {
		cmd1.value.value, cmd2.value.value = val1, val2
		cmd1.valueCmd, cmd2.valueCmd = "", ""
		droid.output <- cmd1
		droid.output <- cmd2
	} else {
		cmd1.value.value, cmd2.value.value = val2, val1
		cmd1.valueCmd, cmd2.valueCmd = "", ""
		droid.output <- cmd1
		droid.output <- cmd2
	}
}

//Dispatcher sends commands from the input to bots or values (received from drones) to drones/output bins
func dispatcher(commands chan command) {
	droids := make(map[int]droid)
	bins = make(map[int]int)
	for cmd := range commands {
		if cmd.valueCmd != "" { // Send a command to a bot
			bot := getBot(droids, cmd.source, commands)
			bot.commands <- cmd
		} else { // Send a value to a bot
			if cmd.destType == "bot" {
				bot := getBot(droids, cmd.destNumber, commands)
				bot.input <- cmd.value.value
			} else {
				bins[cmd.destNumber] = cmd.value.value
			}
		}
	}
}

//Gets the references to a bot if it is already launched
//If the bot hasn't been launched yet, it will be launched
func getBot(droids map[int]droid, id int, output chan command) *droid {
	drone, exists := droids[id]
	if !exists {
		drone = droid{make(chan int, 2), make(chan command, 2), id, output}
		droids[id] = drone
		go bot(&drone) //Launch
	}
	return &drone
}

func endCondition(i, j int) func(int, int) bool {
	return func(k, m int) bool {
		return k == i && m == j || k == j && m == i
	}
}

//Read the input, create one or more commands from each input line
func readInput(commands chan command) {
	handover = regexp.MustCompile(`bot (\d+) gives (low|high) to (bot|output) (\d+) and (low|high) to (bot|output) (\d+)`)
	inputbin = regexp.MustCompile(`value (\d+) goes to bot (\d+)`)
	file, _ := os.Open("input.txt")
	defer file.Close()
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		sendCommands(scanner.Text(), commands)
	}
}

//Send commands to the dispatcher
func sendCommands(line string, commands chan command) {
	cmd := handover.FindStringSubmatch(line) //1=botid 2=loworhigh 3=botoroutput 4=destid 5=loworhighh 6=botoroutput 7=destid2
	if len(cmd) > 0 {
		commands <- command{source: toInt(cmd[1]), valueCmd: cmd[2], value: value{destType: cmd[3], destNumber: toInt(cmd[4])}}
		commands <- command{source: toInt(cmd[1]), valueCmd: cmd[5], value: value{destType: cmd[6], destNumber: toInt(cmd[7])}}
	} else if cmd = inputbin.FindStringSubmatch(line); len(cmd) > 0 { //1=value 2=botid
		commands <- command{value{value: toInt(cmd[1]), destNumber: toInt(cmd[2]), destType: "bot"}, "", 0}
	} else {
		panic("Could not parse: " + line)
	}

}

func toInt(s string) (i int) {
	i, _ = strconv.Atoi(s)
	return
}

func track(start time.Time, name string) {
	fmt.Printf("%s took %s \n", name, time.Since(start))
}
