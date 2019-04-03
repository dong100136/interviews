int[] pos = new int[input1];
  int sum = 0;
  int first = 0;
  for(int n = 0; n < input1; n++){
   pos[n] = n + 1;
  }
  for(int i= 0; i < input2; i++){
   if(input3[i][0] == 1){
    //first person moves out
    pos[first] = 0;
    first++;
    for(int j = input1 - 1; j > 0; j--){
     if(pos[j] == 0){
      continue;
     }
     pos[j] = pos[j] - 1;
    }
   }else if(input3[i][0] == 2){
    //X moves out
    pos[input3[i][1] - 1] = 0;
    for(int j = input1 - 1; j >= input3[i][1]; j--){
     if(pos[j] == 0){
      continue;
     }
     pos[j] = pos[j] - 1;
    }
   }else{
    //query
    sum += pos[input3[i][1] - 1];
   }
   
  }
  return sum;