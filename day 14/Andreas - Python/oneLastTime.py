import hashlib
import sys

salt = "qzyelonm" #"abc"
KEYS_LEFT = 64


def hash(counter, salt=salt):
    m = hashlib.md5()
    m.update(salt.encode('utf-8'))
    m.update(str(counter).encode('utf-8'))
    return str(m.hexdigest())

#It contains three of the same character in a row, like 777. Only consider the first such triplet in a hash.
def firstLevelCandidate(hash):
    i = 0
    while i < len(hash) - 3 and (hash[i] != hash[i+1] or hash[i] != hash[i+2]):
        i += 1

    if hash[i] == hash[i+1] and hash[i] == hash[i+2]:
        return str(hash[i])

    return None

#One of the next 1000 hashes in the stream contains that same character five times in a row, like 77777.
def secondLevelCandidate(hash): #TODO == candidatechar
    found = False
    i = 0
    while i < len(hash) - 5 and not found:
        
        j = i
        while j < len(hash) - 5 and hash[i] == hash[j]:
            j+=1

        found = j == i + 5
        i += 1

    if found:
        return str(hash[i])
    else:
        return None

counter = 0
fl_candidates = list() #[index, char, hasMatch] of candidates with a tripplet
keys = list()
while len(keys) < KEYS_LEFT and counter < 19641:
    val = hash(counter)

    sl_char = secondLevelCandidate(val)
    if sl_char is not None: 
        #set match, but only add to keys when all fl_candidates in front are matches or removed
        for cand in fl_candidates:
            if sl_char == cand[1] and counter < cand[0] + 1000:
                #print("\tsc_candidate at ", counter, " char ", sl_char, " ", val, " for ", cand[0])
                cand[2] = True

    #prune
    if len(fl_candidates) > 0 and fl_candidates[0][0] < counter - 1000 and not fl_candidates[0][2]:
        #print("\tpruning ", cand)
        fl_candidates.remove(fl_candidates[0]) 

    #add key if needed
    while len(fl_candidates) > 0 and fl_candidates[0][2]:
        #print("Found key: ", fl_candidates[0])
        keys.append(fl_candidates[0])
        fl_candidates.remove(fl_candidates[0]) #remove from candidates


    #add new first lvl candidates if needed
    char = firstLevelCandidate(val)
    if char != None:
        #print("fl_candidate at ", counter, " ", val, " ", char)
        fl_candidates.append([counter,char,False])

    counter += 1


for k in keys:
    print(k)
print()
print(len(keys))
