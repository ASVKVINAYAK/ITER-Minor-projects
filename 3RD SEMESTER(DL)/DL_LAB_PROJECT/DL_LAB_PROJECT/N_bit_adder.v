`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:46:01 02/07/2021 
// Design Name: 
// Module Name:    N_bit_adder 
// Project Name: 
// Target Devices: 
// Tool versions: 
// Description: 
//
// Dependencies: 
//
// Revision: 
// Revision 0.01 - File Created
// Additional Comments: 
//
//////////////////////////////////////////////////////////////////////////////////
module N_bit_adder(a,b,sum,carry_out);
parameter N=2;
input [N-1:0] a,b;
   output [N-1:0] sum;
   output  carry_out;
  wire [N-1:0] carry;
   genvar i;
   generate 
   for(i=0;i<N;i=i+1)
     begin: generate_N_bit_Adder
   if(i==0) 
  half_adder ha(a[0],b[0],sum[0],carry[0]);
   else
  full_adder fa(a[i],b[i],carry[i-1],sum[i],carry[i]);
     end
  assign carry_out = carry[N-1];
   endgenerate
endmodule
