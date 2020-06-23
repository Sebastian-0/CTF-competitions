 
(defun frobnicate(str xor offset lvl)
  (setq mead (cheekybreeky (+ xor offset)))


  ;(write-line (format nil "~D ~D ~D" xor offset lvl))
  
  (cond ((< xor (- offset 1))
        ;;(princ (logxor (- (char-int (char str mead)) (char-int #\0)) 42))
        (princ (char str mead))
        (princ "/")
;;        (if (equal lvl 3)
;;          (setq mead (cheekybreeky 16))
;;        )
        ;(write-line (format nil "~D ~D invoking ~D ~D and ~D ~D" xor offset xor mead (+ mead 1) offset))
        
        (frobnicate str xor mead (+ lvl 1))
        (frobnicate str (+ mead 1) offset (+ lvl 1))
    )
    ;(
    ;  t 0
    ;)
  )
)

(defun cheekybreeky (num)
  (setq n 0)
  (loop
    (if (>= (* n 2) num)
       (return)
    )
    (setq n (+ 1 n))
  )

  (if (equal (* n 2) num)
    (return-from cheekybreeky n)
    (return-from cheekybreeky (- n 1))
  )
)

(defun hello()
  (setq flag "your flag is in another castle")
;  (setq flag "you")
  (frobnicate flag 0 (length flag) 0.0)
  (princ (cheekybreeky 0))
)

(hello)