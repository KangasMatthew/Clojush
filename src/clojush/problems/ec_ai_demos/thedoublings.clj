;; thedoublings.clj
;; an example problem for clojush, a Push/PushGP system written in Clojure
;; Nic McPhee, mcphee@morris.umn.edu, 2016

(ns clojush.problems.ec-ai-demos.thedoublings
  (:use [clojush.pushgp.pushgp]
        [clojush.random]
        [clojush pushstate interpreter]
        clojush.instructions.common))

;;;;;;;;;;;;


(def input-set
  ["DD" "NN" "EE" "AA" "KK" "QQ" "SS" "EE" "EE" "TT"
   "UK" "KD" "EW" "FD" "QX" "VW" "RX" "XV" "FC" "GA"
   "YRYR" "UUUU" "PLPL" "VVVV" "JLJL" "AWAW" "TFTF" "DSDS" "ATAT" "CMCM"
   "IUDV" "TUSW" "ORCE" "SIBS" "XJBA" "WERT" "OPVJ" "NJSY" "XPSZ" "LBJW"
   "YUGYUG" "MYAMYA" "YSRYSR" "CJRCJR" "SZXSZX" "YZFYZF" "PCPPCP" "PIMPIM" "SJASJA" "LEELEE"
   "WCCNLV" "BLZKYQ" "RQMTTR" "IVLZYP" "WOSUDM" "PBNBWF" "PJLHEO" "ZXRANJ" "ONYRHU" "FFXCUS"
   "TQCRTQCR" "MHMAMHMA" "NBXJNBXJ" "NFTBNFTB" "WZZMWZZM" "MMBBMMBB" "RXOWRXOW" "IOJIIOJI" "CWOVCWOV" "OUIYOUIY"
   "WQSUXPPQ" "JGKFQMPU" "TEINRXAL" "HPSTCZBM" "YXEDDKNO" "YTKAROTY" "BMDJHZVQ" "GNXWHHVK" "NVCEEDUW" "LKXNWKCA"
   "IZLZEIZLZE" "RCRPXRCRPX" "YYPQAYYPQA" "IIEPOIIEPO" "GNNEAGNNEA" "DHQLVDHQLV" "KXSXWKXSXW" "ZPJAOZPJAO" "NHVULNHVUL" "DPSJVDPSJV"
   "PMIHKMFTOU" "WQLCYOYSMX" "XJVWOZLKSD" "RMMSXGARXH" "SMZHUALQZS" "WFOAUGNADC" "BZVEMAUKFR" "HAUDGHDLVD" "PUPRLUHGQJ" "BNLPKVMRXX"
   "GCOHBAGCOHBA" "MGRLDSMGRLDS" "YVIWXGYVIWXG" "JUBQQJJUBQQJ" "BBXEYRBBXEYR" "VJACEMVJACEM" "UGVIATUGVIAT" "TICCMHTICCMH" "QPTUZZQPTUZZ" "VIDDTRVIDDTR"
   "GWITUGKDJRKK" "FUYMLBRAZZXU" "BKYORAXEUAGF" "AFUXQOMSXYOV" "CLQIOWHSYORI" "OURQUCFFPIPF" "UGZKTJUMRNQX" "EJQWILYSAUHI" "YGDVGYLUKPOQ" "OLVZFMTNUBZB"])

(defn expected-output
  [string]
  (apply = (map str (split-at (/ (count string) 2) string))))

; Make a new push state, and then add every
; input to the special `:input` stack.
; You shouldn't have to change this.
(defn make-start-state
  [inputs]
  (reduce (fn [state input]
            (push-item input :input state))
          (make-push-state)
          inputs))

; The only part of this you'd need to change is
; which stack(s) the return value(s) come from.
(defn actual-output
  [program inputs]
  (let [start-state (make-start-state inputs)
        end-state (run-push program start-state)
        result (top-item :boolean end-state)]
    result))

(defn all-errors
  [program]
  (doall
    (for [inputs input-set]
      (let [expected (expected-output inputs)
            actual (actual-output program inputs)]
        (if (= expected actual)
          0
          1)))))

(def atom-generators
  (concat
    ; Include all the instructions that act on integers and booleans
    ; Could have :exec here, but I just am limiting things to exec-if
    (registered-for-stacks [:string])
    (list 'integer_div 2)
    ; A bunch of random numbers in case that's useful.
    ; (list (fn [] (lrand-int 100)))
    ; The two input
    (list 'in1)))

(def argmap
  {:error-function all-errors
   :atom-generators atom-generators
   :population-size 500
   :max-generations 300
   })
