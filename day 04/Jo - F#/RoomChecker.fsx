#r @"lib\FSharp.Text.RegexProvider.dll"
open FSharp.Text.RegexProvider

//Strong typing on regex group names. Just because we can!
type RoomRegex = Regex<"(?<name>.*)-(?<zone>\d*)\[(?<checksum>.*)\]">
type Room = {Name : char list ; Zone : int ; Checksum : char list}

let split (separator : string) (line : string) = 
    line.Split([|separator|], System.StringSplitOptions.None)
    |> List.ofSeq

let parseName name =
    split "-" name
    |> List.collect (List.ofSeq)

let parse room =
    let matcher = new RoomRegex()
    match matcher.Match(room) with
    | hit when hit.Success ->
        let nameParts = parseName hit.name.Value
        let zone = System.Int32.Parse hit.zone.Value
        let checksum = hit.checksum.Value |> List.ofSeq
        { Name = nameParts; Zone = zone; Checksum = checksum }
    | _ -> failwithf "This room name does not correspond to the expected pattern: %s" room

let rank places letters =
    letters
    |> Seq.countBy id
    |> Seq.groupBy (fun (_, occurences) -> occurences) 
    |> Seq.sortByDescending (fun (occurences,_) -> occurences) //Highest number of occurences first
    |> Seq.collect (fun (_, grouping) -> grouping |> Seq.sort) //Then alphabetically for same number of occurences.
    |> Seq.take places
    |> Seq.map (fun (char, _) -> char)
    |> Seq.toList

let realRoom { Name = name; Checksum = checksum } =
    let topFiveLetters = rank 5 name
    topFiveLetters = checksum

let sumOfValidRoomZones roomNames =
    roomNames
    |> Seq.map parse
    |> Seq.filter realRoom
    |> Seq.sumBy (fun r -> r.Zone)

let shift offset (letter : char) = 
    let indexOfA = 'a' |> int

    let offsetFromA = (letter |> int) - indexOfA
    let wraparound = (offsetFromA + offset) % 26
    let shifted = indexOfA + wraparound
    shifted |> char

let decrypt room = 
    room.Name
    |> Seq.map (shift room.Zone)
    |> Seq.map string
    |> String.concat ""