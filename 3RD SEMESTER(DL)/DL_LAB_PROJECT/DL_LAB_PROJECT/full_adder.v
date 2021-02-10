`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:42:44 02/07/2021 
// Design Name: 
// Module Name:    full_adder 
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
module full_adder(x,y,carry_in,sum,carry_out);
   input x,y,carry_in;
   output sum,carry_out;
 assign sum = (x^y) ^ carry_in;
 assign carry_out = (y&&carry_in)|| (x&&y) || (x&&carry_in);
endmodule 
