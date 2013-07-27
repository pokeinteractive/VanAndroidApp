<?

$config = array(
                 'signUp' => array(
                                    array(
                                            'field' => 'screen_name',
                                            'label' => '顯示名稱',
                                            'rules' => 'required|callback_screen_name_check'
                                         )
                                    ),
                 'serviceToAdd' => array(
                                    array(
                                            'field' => 'service_name',
                                            'label' => '服務名稱',
                                            'rules' => 'required'
                                         ),
                                    array(
                                            'field' => 'description',
                                            'label' => 'description',
                                            'rules' => ''
                                         )                                         
                                    ),
                  'sellProductToAdd' => array(
                                    array(
                                            'field' => 'sell_cat_id',
                                            'label' => '物品分類',
                                            'rules' => 'required'
                                         ),
                                    array(
                                            'field' => 'name',
                                            'label' => '物品名稱',
                                            'rules' => 'required'
                                         ),                                         
                                    array(
                                            'field' => 'price',
                                            'label' => '價格',
                                            'rules' => 'required'
                                         ),                                         
                                    array(
                                            'field' => 'qty',
                                            'label' => '數量',
                                            'rules' => 'required'
                                         )                                         
                                    )                                                             
               );

?>