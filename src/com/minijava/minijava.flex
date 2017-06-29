package com.minijava;

import java_cup.runtime.*;

%%

%public
%final
%class Scanner
%unicode
%cup
%line
%column

%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }

  // Retorna um representacao legivel do simbolo s
  public String symbolToString(Symbol s) {
    String rep;
    switch (s.sym) {
      case sym.BECOMES: return "BECOMES";
      case sym.SEMICOLON: return "SEMICOLON";
      case sym.COMMA: return "COMMA";
      case sym.PLUS: return "PLUS";
      case sym.MINUS: return "MINUS";
      case sym.LPAREN: return "LPAREN";
      case sym.RPAREN: return "RPAREN";
      case sym.LBRACKET: return "LBRACKET";
      case sym.RBRACKET: return "RBRACKET";
      case sym.LCURL: return "LCURL";
      case sym.RCURL: return "RCURL";
      case sym.MULT: return "MULT";
      case sym.IF: return "IF";
      case sym.ELSE: return "ELSE";
      case sym.THIS: return "THIS";
      case sym.WHILE: return "WHILE";
      case sym.NOT: return "NOT";
      case sym.DOT: return "DOT";
      case sym.LENGTH: return "LENGTH";
      case sym.NEW: return "NEW";
      case sym.INT: return "INT";
      case sym.BOOLEAN: return "BOOLEAN";
      case sym.PUBLIC: return "PUBLIC";
      case sym.CLASS: return "CLASS";
      case sym.STATIC: return "STATIC";
      case sym.VOID: return "VOID";
      case sym.MAIN: return "MAIN";
      case sym.STRING: return "STRING";
      case sym.EXTENDS: return "EXTENDS";
      case sym.RETURN: return "RETURN";
      case sym.LESS: return "LESS";
      case sym.BAND: return "BAND";
      case sym.TRUE: return "TRUE";
      case sym.FALSE: return "FALSE";
      case sym.PRINT: return "PRINT";
      case sym.INTEGER: return "INTEGER(" + (String)s.value + ")";
      case sym.IDENTIFIER: return "ID(" + (String)s.value + ")";
      case sym.EOF: return "<EOF>";
      case sym.error: return "<ERROR>";
      default: return "<UNEXPECTED TOKEN " + s.toString() + ">";
    }
  }
%}

letter = [a-zA-Z]
digit = [0-9]
pos_digit = [1-9]
eol = [\r\n]
white = {eol}|[ \t]
input_character = [^\r\n]

%%

"System.out.println" { return symbol(sym.PRINT); }

/* Operadores */
"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"=" { return symbol(sym.BECOMES); }
"*" { return symbol(sym.MULT); }
"<" { return symbol(sym.LESS); }
"&&" { return symbol(sym.BAND); }
"!" { return symbol(sym.NOT); }

/* Delimitadores */
"[" { return symbol(sym.LBRACKET); }
"]" { return symbol(sym.RBRACKET); }
"{" { return symbol(sym.LCURL); }
"}" { return symbol(sym.RCURL); }
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
";" { return symbol(sym.SEMICOLON); }
"," { return symbol(sym.COMMA); }

/* Tipos */
"boolean" { return symbol(sym.BOOLEAN); }
"int" { return symbol(sym.INT); }
"true" { return symbol(sym.TRUE); }
"false" { return symbol(sym.FALSE); }

/* Outros */
"." { return symbol(sym.DOT); }
"length" { return symbol(sym.LENGTH); }
"this" { return symbol(sym.THIS); }
"new" { return symbol(sym.NEW); }
"public" { return symbol(sym.PUBLIC); }
"class" { return symbol(sym.CLASS); }
"static" { return symbol(sym.STATIC); }
"void" { return symbol(sym.VOID); }
"main" { return symbol(sym.MAIN); }
"String" { return symbol(sym.STRING); }
"extends" { return symbol(sym.EXTENDS); }
"return" { return symbol(sym.RETURN); }
"while" { return symbol(sym.WHILE); }
"if" { return symbol(sym.IF); }
"else" { return symbol(sym.ELSE); }

/* INTEGER*/
{digit}|{pos_digit}{digit}* { return symbol(sym.INTEGER, yytext()); }

/* Identificadores */
{letter} ({letter}|{digit}|_)* { return symbol(sym.IDENTIFIER, yytext()); }

/* espaco em branco */
{white}+ { /* Espaco Ignorado */ }

/* Ignorar comentarios como este */
"/*" ~"*/" { /* ignore comment */ }

/* Erros */
. {
    System.err.println(
        "\nunexpected character in input: '" + yytext() + "' at line " +
        (yyline+1) + " column " + (yycolumn+1));
    return symbol(sym.error);
  }