#r ".\lib\Unquote.dll"
open Swensen.Unquote
open System.Text.RegularExpressions

let debug = (fun x -> printfn "DEBUG: %A" x; x)    

type IPAddress = { supernets : char list list; hypernets : char list list }

let parse (iptext : string) =
    let allParts = iptext.Split([|'[';']'|]) |> List.ofSeq
    let (evens, odds) = 
        allParts 
        |> List.indexed 
        |> List.partition (fun (index, part) -> index % 2 = 0)
    { supernets = evens |> List.map (snd >> List.ofSeq); hypernets = odds |> List.map (snd >> List.ofSeq) }

let rec findABAsInSingle accumulator supernet =
    match supernet with
    | [] -> accumulator
    | a :: b :: c :: rest when a = c && a <> b -> findABAsInSingle ((a,b,c) :: accumulator) (b :: c :: rest)
    | _ :: rest -> findABAsInSingle accumulator rest

let findABAs supernets =
    supernets
    |> List.collect (findABAsInSingle [])
    |> List.distinct

let toBAB aba = 
    let (a,b,c) = aba
    (b,a,b)

let rec isPresentIn hypernet bab = 
    let (a1,b1,c1) = bab
    match hypernet with
    | [] -> false
    | a2 :: b2 :: c2 :: rest when a1 = a2 && b1 = b2 && c1 = c2 -> true
    | _ :: rest -> isPresentIn rest bab

let containsAnyBAB babs hypernet =
    babs 
    |> List.exists (isPresentIn hypernet)
let supportsSSL ip = 
    let allABAs = 
        findABAs ip.supernets
    let allBABs = 
        allABAs 
        |> List.map toBAB
    ip.hypernets
    |> List.exists (containsAnyBAB allBABs)

test <@ parse "ab[cd]ef" = {supernets = [['a';'b'];['e';'f']]; hypernets = [['c';'d']]} @>
test <@ parse "ab[cd]ef[gh]ij[kl]mn" = {supernets = [['a';'b'];['e';'f'];['i';'j'];['m';'n']]; hypernets = [['c';'d'];['g';'h'];['k';'l']]} @>

test <@ "aba[bab]xyz" |> parse |> supportsSSL @>
test <@ not ("xyx[xyx]xyx" |> parse |> supportsSSL) @>
test <@ "aaa[kek]eke" |> parse |> supportsSSL @>
test <@ "zazbz[bzb]cdb" |> parse |> supportsSSL @>

let input = "rhamaeovmbheijj[hkwbkqzlcscwjkyjulk]ajsxfuemamuqcjccbc
gdlrknrmexvaypu[crqappbbcaplkkzb]vhvkjyadjsryysvj[nbvypeadikilcwg]jwxlimrgakadpxu[dgoanojvdvwfabtt]yqsalmulblolkgsheo
dqpthtgufgzjojuvzvm[eejdhpcqyiydwod]iingwezvcbtowwzc[uzlxaqenhgsebqskn]wcucfmnlarrvdceuxqc[dkwcsxeitcobaylhbvc]klxammurpqgmpsxsr
gmmfbtpprishiujnpdi[wedykxqyntvrkfdzom]uidgvubnregvorgnhm"

input.Split('\n')
|> Seq.map parse
|> Seq.filter supportsSSL
|> Seq.length