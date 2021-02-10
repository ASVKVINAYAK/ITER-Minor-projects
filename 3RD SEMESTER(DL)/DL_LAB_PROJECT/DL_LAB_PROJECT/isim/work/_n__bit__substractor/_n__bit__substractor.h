////////////////////////////////////////////////////////////////////////////////
//   ____  ____  
//  /   /\/   /  
// /___/  \  /   
// \   \   \/    
//  \   \        Copyright (c) 2003-2004 Xilinx, Inc.
//  /   /        All Right Reserved. 
// /___/   /\   
// \   \  /  \  
//  \___\/\___\ 
////////////////////////////////////////////////////////////////////////////////

#ifndef H_workM_n__bit__substractor_H
#define H_workM_n__bit__substractor_H

#ifdef _MSC_VER
#pragma warning(disable: 4355)
#endif

#ifdef __MINGW32__
#include "xsimMinGW.h"
#else
#include "xsim.h"
#endif

class workM_n__bit__substractor : public HSim__s5{
public: 
    workM_n__bit__substractor(const char *instname);
    ~workM_n__bit__substractor();
    void setDefparam();
    void constructObject();
    void moduleInstantiate(HSimConfigDecl *cfg);
    void connectSigs();
    void reset();
    virtual void archImplement();
    void genModuleInstantiate(HSimConfigDecl *cfg);
    void genParamAssign();
    void genSetDefparam();
    void genParamValue(HSimConfigDecl *cfg);
    class cu0 : public HSim__s6 {
    public:
        cu0(workM_n__bit__substractor* arch);
        ~cu0();
        void constructObject();
        void moduleInstantiate(HSimConfigDecl *cfg);
        void setDefparam();
        void archImplement();
        void connectSigs();
        workM_n__bit__substractor* Arch;
        HSim::ValueS u3[1];
        cu0* u0;
        class cu1 : public HSim__s6 {
        public:
            cu1(cu0* arch);
            ~cu1();
            void constructObject();
            void moduleInstantiate(HSimConfigDecl *cfg);
            void setDefparam();
            void archImplement();
            void connectSigs();
            cu0* Arch;
        };
        cu1* u1;
        class cu2 : public HSim__s6 {
        public:
            cu2(cu0* arch);
            ~cu2();
            void constructObject();
            void moduleInstantiate(HSimConfigDecl *cfg);
            void setDefparam();
            void archImplement();
            void connectSigs();
            cu0* Arch;
        };
        cu2* u2;
    };
    cu0* u0;
    HSim__s1 us[5];
    HSimVlogParam up[1];
};

#endif
