#r @"..\..\0 lib\F#\Unquote.dll"
open Swensen.Unquote

let switch =
    function
    | '0' -> '1'
    | '1' -> '0'
    | unknown -> failwithf "That's not a bit: %c" unknown

let double word =
    let a = word
    let b = 
        a
        |> List.rev
        |> List.map switch
    a @ ['0'] @ b

let rec lengthen increaser maxLength word =
    if maxLength <= List.length word then
        word
    else
        lengthen increaser maxLength (increaser word)

let isEven number = number % 2 = 0
let pairs list =
    list
    |> List.pairwise
    |> List.indexed
    |> List.filter (fst >> isEven)
    |> List.map snd

let compress =
    function
    | (a,b) when a = b -> '1'
    | _ -> '0'

let rec checksum word = 
    let compressed = 
        word
        |> pairs
        |> List.map compress
    if compressed |> List.length |> isEven then
        checksum compressed
    else
        compressed

let printAndContinue msg stream =
    printfn msg;
    stream
let checksumForDataOfLength l (input : string) =
    lengthen double l (input |> Seq.toList)
    |> printAndContinue "found the random data!"
    |> List.truncate l
    |> printAndContinue "truncated data!"
    |> checksum
    |> printAndContinue "checksummed it!"
    |> Seq.map string
    |> String.concat ""

printfn "Testing..."
let toString = Seq.map string >> String.concat ""
let testDouble = Seq.toList >> double >> toString
test <@ testDouble "1" = "100" @>
test <@ testDouble "0" = "001" @>
test <@ testDouble "11111" = "11111000000" @>
test <@ testDouble "111100001010" = "1111000010100101011110000" @>

test <@ lengthen (fun x -> 'a' :: x) 3 ['b'] = ['a';'a';'b'] @>

test <@ pairs [1;2;3;4] = [(1,2); (3,4)] @>

let testChecksum = Seq.toList >> checksum >> toString
test <@ testChecksum "110010110100" = "100" @>

test <@ checksumForDataOfLength 20 "10000" = "01100" @>
printfn "Testing done!"

let input = "01000100010010111"

printfn "Cracking..."
#time
checksumForDataOfLength 35651584 input
|> printfn "The correct checksum is: %s"