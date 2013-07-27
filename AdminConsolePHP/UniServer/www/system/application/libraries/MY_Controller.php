<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Base_Controller extends Controller {

	var $headerTitle;
	
	/**
	 * Constructor
	 *
	 * Calls the initialize() function
	 */
	function Base_Controller()
    {
        parent::Controller();
        // anything you want to do in every controller, you shall perform here.
        
		$this->lang->load('header', 'chinese');
		$titlekey = $this->uri->segment(1) . "/" . $this->uri->segment(2);
        $this->headerTitle['preTitle'] = $this->lang->line($titlekey);
        //echo $titlekey . $this->headerTitle['preTitle'];
        $this->headerTitle['headerTitle'] = "";
        $this->headerTitle['search'] = "";
        $this->headerTitle['showLogin'] = false;
        $this->headerTitle['pageTitle'] = "";
    }

}
// END _Controller class

/* End of file Controller.php */
/* Location: ./system/libraries/Controller.php */